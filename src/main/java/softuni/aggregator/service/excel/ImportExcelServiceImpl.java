package softuni.aggregator.service.excel;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import softuni.aggregator.domain.entities.*;
import softuni.aggregator.domain.repository.CompanyRepository;
import softuni.aggregator.domain.repository.EmployeeRepository;
import softuni.aggregator.domain.repository.MajorIndustryRepository;
import softuni.aggregator.domain.repository.MinorIndustryRepository;
import softuni.aggregator.utils.excel.reader.ExcelReader;
import softuni.aggregator.utils.excel.reader.model.CompanyExcelDto;
import softuni.aggregator.utils.excel.reader.model.EmployeeExcelDto;
import softuni.aggregator.utils.excel.reader.model.OrbisCompanyDto;
import softuni.aggregator.utils.excel.reader.model.XingCompanyDto;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Log
@Service
@Transactional
@SuppressWarnings("unchecked")
public class ImportExcelServiceImpl implements ImportExcelService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final MinorIndustryRepository minorIndustryRepository;
    private final MajorIndustryRepository majorIndustryRepository;
    private final ServletContext servletContext;
    private final ExcelReader xingReader;
    private final ExcelReader orbisReader;
    private final ExcelReader employeesReader;

    @Autowired
    public ImportExcelServiceImpl(CompanyRepository companyRepository, EmployeeRepository employeeRepository,
                                  MinorIndustryRepository minorIndustryRepository,
                                  MajorIndustryRepository majorIndustryRepository, ServletContext servletContext,
                                  @Qualifier("xing") ExcelReader xingReader,
                                  @Qualifier("orbis") ExcelReader orbisReader,
                                  @Qualifier("employees") ExcelReader employeesReader) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
        this.minorIndustryRepository = minorIndustryRepository;
        this.majorIndustryRepository = majorIndustryRepository;
        this.servletContext = servletContext;
        this.xingReader = xingReader;
        this.orbisReader = orbisReader;
        this.employeesReader = employeesReader;
    }

    @Override
    public void importCompaniesFromXing(MultipartFile multipartFile) {
        File file = saveTempFile(multipartFile);
        List<XingCompanyDto> data = xingReader.readExcel(file.getAbsolutePath());
        deleteFile(file);

        Map<String, MajorIndustry> majorIndustriesMap = majorIndustryRepository.findAll().stream()
                .collect(Collectors.toMap(MajorIndustry::getName, i -> i));

        Map<String, MinorIndustry> minorIndustriesMap = minorIndustryRepository.findAll().stream()
                .collect(Collectors.toMap(MinorIndustry::getName, i -> i));

        List<String> companyWebsites = data.stream()
                .map(XingCompanyDto::getWebsite)
                .collect(Collectors.toList());

        Map<String, Company> companies = getCompaniesMap(companyWebsites);

        for (XingCompanyDto companyDto : data) {
            Company company = companies.getOrDefault(companyDto.getWebsite(), new Company());
            setXingCompanyProperties(company, companyDto, majorIndustriesMap, minorIndustriesMap);
            companies.putIfAbsent(company.getWebsite(), company);
        }

        companyRepository.saveAll(companies.values());
    }

    @Override
    public void importCompaniesFromOrbis(MultipartFile multipartFile) {
        File file = saveTempFile(multipartFile);
        List<OrbisCompanyDto> data = orbisReader.readExcel(file.getAbsolutePath());
        deleteFile(file);

        List<String> companyWebistes = data.stream()
                .map(OrbisCompanyDto::getWebsite)
                .collect(Collectors.toList());

        Map<String, Company> companies = getCompaniesMap(companyWebistes);

        for (OrbisCompanyDto companyDto : data) {
            Company company = companies.getOrDefault(companyDto.getWebsite(), new Company());
            setOrbisCompanyProperties(company, companyDto);
            companies.putIfAbsent(company.getWebsite(), company);
        }
        companyRepository.saveAll(companies.values());
    }

    @Override
    public void importEmployees(MultipartFile multipartFile) {
        File file = saveTempFile(multipartFile);
        List<EmployeeExcelDto> data = employeesReader.readExcel(file.getAbsolutePath());
        deleteFile(file);

        List<Employee> employees = new ArrayList<>();

        for (EmployeeExcelDto employeeDto : data) {
            Employee employee = employeeRepository.findByEmail(employeeDto.getEmail())
                    .orElse(new Employee());
            setEmployeeProperties(employeeDto, employee);
            employees.add(employee);
        }

        employeeRepository.saveAll(employees);
    }

    private void setXingCompanyProperties(Company company, XingCompanyDto companyDto, Map<String,
            MajorIndustry> majorIndustryMap, Map<String, MinorIndustry> minorIndustryMap) {

        CompanyDetails companyDetails = company.getCompanyDetails();
        if (companyDetails == null) {
            companyDetails = new CompanyDetails();
        }

        setCommonCompanyProperties(company, companyDetails, companyDto);

        companyDetails.setEmployeesRange(companyDto.getEmployeesRange());
        companyDetails.setStreet(companyDto.getStreet());
        companyDetails.setFax(companyDto.getFax());
        companyDetails.setCompanyProfileLink(companyDto.getCompanyProfileLink());
        companyDetails.setYearFound(getPropertyValueAsInteger(companyDto.getYearFound()));
        companyDetails.setInformation(companyDto.getInformation());
        companyDetails.setCompanyProfileLink(companyDto.getCompanyProfileLink());

        company.setCompanyDetails(companyDetails);

        String majorIndustryName = companyDto.getXingIndustry1();
        MajorIndustry majorIndustry = majorIndustryMap
                .getOrDefault(majorIndustryName, new MajorIndustry(companyDto.getXingIndustry1()));

        majorIndustryMap.putIfAbsent(majorIndustryName, majorIndustry);

        String minorIndustryName = companyDto.getXingIndustry2();
        MinorIndustry minorIndustry = minorIndustryMap
                .getOrDefault(minorIndustryName, new MinorIndustry(minorIndustryName, majorIndustry));

        minorIndustryMap.putIfAbsent(minorIndustryName, minorIndustry);

        company.setIndustry(minorIndustry);
    }

    private void setOrbisCompanyProperties(Company company, OrbisCompanyDto companyDto) {
        CompanyDetails companyDetails = company.getCompanyDetails();
        if (companyDetails == null) {
            companyDetails = new CompanyDetails();
        }
        setCommonCompanyProperties(company, companyDetails, companyDto);

        companyDetails.setVATNumber(companyDto.getVATNumber());
        companyDetails.setBvDIdNumber(companyDto.getBvDIdNumber());
        companyDetails.setISOCountryCode(companyDto.getISOCountryCode());
        companyDetails.setNaceRevMainSection(companyDto.getNaceRevMainSection());
        companyDetails.setNaceRevCoreCode(getPropertyValueAsInteger(companyDto.getNaceRevCoreCode()));
        companyDetails.setConsolidationCode(companyDto.getConsolidationCode());
        companyDetails.setOperatingIncome(companyDto.getOperatingIncome());
        companyDetails.setEmployeesCount(getPropertyValueAsInteger(companyDto.getEmployeesCount()));
        companyDetails.setAddress(companyDto.getAddress());
        companyDetails.setJobDescription(companyDto.getJobDescription());
        companyDetails.setStandardizedLegalForm(companyDto.getStandardizedLegalForm());
        companyDetails.setManagersCount(getPropertyValueAsInteger(companyDto.getManagersCount()));
        companyDetails.setCorporationCompaniesCount(getPropertyValueAsInteger(companyDto.getCorporationCompaniesCount()));
        companyDetails.setSubsidiariesCount(getPropertyValueAsInteger(companyDto.getSubsidiariesCount()));

        company.setCompanyDetails(companyDetails);
    }

    private void setCommonCompanyProperties(Company company, CompanyDetails companyDetails, CompanyExcelDto companyDto) {
        company.addEmail(companyDto.getCompanyEmail());
        company.setName(companyDto.getName());
        company.setWebsite(companyDto.getWebsite());
        companyDetails.setPostcode(getPropertyValueAsInteger(companyDto.getPostcode()));
        companyDetails.setCity(companyDto.getCity());
        companyDetails.setCountry(companyDto.getCountry());
        companyDetails.setCompanyPhone(companyDto.getCompanyPhone());
    }

    private void setEmployeeProperties(EmployeeExcelDto employeeDto, Employee employee) {
        Company company = companyRepository.findByName(employeeDto.getCompanyName()).orElse(null);

        employee.setCompany(company);
        employee.setEmail(employeeDto.getEmail());
        employee.setFullName(employeeDto.getFullName());
        employee.setHunterIoScore(getPropertyValueAsInteger(employeeDto.getHunterIoScore()));
        employee.setPosition(employeeDto.getPosition());
    }

    private File saveTempFile(MultipartFile multipartFile) {
        String basePath = servletContext.getRealPath("\\");
        String uuid = UUID.randomUUID().toString();
        String filePath = String.format("%s\\%s-%s", basePath, uuid, multipartFile.getOriginalFilename());

        // TODO -> handle
        File file;
        try {
            file = new File(filePath);
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        return file;
    }

    private void deleteFile(File file) {
        if (file.delete()) {
            log.info(String.format("Successfully deleted %s", file.getName()));
        } else {
            log.warning(String.format("Failed to delete %s", file.getName()));
        }
    }

    private Map<String, Company> getCompaniesMap(List<String> companyWebsites) {
        return companyRepository.findAllByWebsiteIn(companyWebsites).stream()
                .collect(Collectors.toMap(Company::getWebsite, c -> c));
    }

    private Integer getPropertyValueAsInteger(String property) {
        return property != null ? Double.valueOf(property).intValue() : null;
    }
}