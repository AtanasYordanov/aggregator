package softuni.aggregator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import softuni.aggregator.domain.entities.*;
import softuni.aggregator.domain.repository.CompanyRepository;
import softuni.aggregator.domain.repository.EmployeeRepository;
import softuni.aggregator.domain.repository.MajorIndustryRepository;
import softuni.aggregator.domain.repository.MinorIndustryRepository;
import softuni.aggregator.service.ImportService;
import softuni.aggregator.service.excel.reader.ExcelReader;
import softuni.aggregator.service.excel.reader.imports.Import;
import softuni.aggregator.service.excel.reader.model.*;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ImportServiceImpl implements ImportService {

    private static final String EXTRACT_INTEGER_REGEX = "\\d+";

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final MinorIndustryRepository minorIndustryRepository;
    private final MajorIndustryRepository majorIndustryRepository;
    private final ServletContext servletContext;
    private final ExcelReader excelReader;

    @Autowired
    public ImportServiceImpl(CompanyRepository companyRepository, EmployeeRepository employeeRepository,
                             MinorIndustryRepository minorIndustryRepository,
                             MajorIndustryRepository majorIndustryRepository, ServletContext servletContext,
                             ExcelReader excelReader) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
        this.minorIndustryRepository = minorIndustryRepository;
        this.majorIndustryRepository = majorIndustryRepository;
        this.servletContext = servletContext;
        this.excelReader = excelReader;
    }

    @Override
    public void importCompaniesFromXing(MultipartFile multipartFile) {
        File file = saveTempFile(multipartFile);
        List<XingCompanyImportDto> data = excelReader.readExcel(file.getAbsolutePath(), Import.XING_COMPANIES);
        deleteFile(file);

        Map<String, MajorIndustry> majorIndustriesMap = majorIndustryRepository.findAll().stream()
                .collect(Collectors.toMap(MajorIndustry::getName, i -> i));

        Map<String, MinorIndustry> minorIndustriesMap = minorIndustryRepository.findAll().stream()
                .collect(Collectors.toMap(MinorIndustry::getName, i -> i));

        List<String> companyWebsites = data.stream()
                .map(XingCompanyImportDto::getWebsite)
                .collect(Collectors.toList());

        Map<String, Company> companies = getCompaniesMap(companyWebsites);

        for (XingCompanyImportDto companyDto : data) {
            Company company = companies.getOrDefault(companyDto.getWebsite(), new Company());
            if (companyDto.getWebsite() != null && !companyDto.getWebsite().isBlank()) {
                setXingCompanyProperties(company, companyDto, majorIndustriesMap, minorIndustriesMap);
                companies.putIfAbsent(company.getWebsite(), company);
            }
        }

        companyRepository.saveAll(companies.values());
        log.info(String.format("Successfully imported %s companies from XING.", companies.size()));
    }

    @Override
    public void importCompaniesFromOrbis(MultipartFile multipartFile) {
        File file = saveTempFile(multipartFile);
        List<OrbisCompanyImportDto> data = excelReader.readExcel(file.getAbsolutePath(), Import.ORBIS_COMPANIES);
        deleteFile(file);

        List<String> companyWebistes = data.stream()
                .map(OrbisCompanyImportDto::getWebsite)
                .collect(Collectors.toList());

        Map<String, Company> companies = getCompaniesMap(companyWebistes);

        for (OrbisCompanyImportDto companyDto : data) {
            Company company = companies.getOrDefault(companyDto.getWebsite(), new Company());
            if (companyDto.getWebsite() != null && !companyDto.getWebsite().isBlank()) {
                setOrbisCompanyProperties(company, companyDto);
                companies.putIfAbsent(company.getWebsite(), company);
            }
        }

        companyRepository.saveAll(companies.values());
        log.info(String.format("Successfully imported %s companies from Orbis.", companies.size()));
    }

    @Override
    public void importEmployees(MultipartFile multipartFile) {
        File file = saveTempFile(multipartFile);
        List<EmployeeImportDto> data = excelReader.readExcel(file.getAbsolutePath(), Import.EMPLOYEES);
        deleteFile(file);

        List<Employee> employees = new ArrayList<>();

        for (EmployeeImportDto employeeDto : data) {
            Employee employee = employeeRepository.findByEmail(employeeDto.getEmail())
                    .orElse(new Employee());
            setEmployeeProperties(employeeDto, employee);
            employees.add(employee);
        }

        employeeRepository.saveAll(employees);
        log.info(String.format("Successfully imported %s employees.", employees.size()));
    }

    private void setXingCompanyProperties(Company company, XingCompanyImportDto companyDto, Map<String,
            MajorIndustry> majorIndustryMap, Map<String, MinorIndustry> minorIndustryMap) {
        setCommonCompanyProperties(company, companyDto);

        company.setEmployeesRange(companyDto.getEmployeesRange());
        company.setStreet(companyDto.getStreet());
        company.setFax(companyDto.getFax());
        company.setCompanyProfileLink(companyDto.getCompanyProfileLink());
        company.setYearFound(getPropertyValueAsInteger(companyDto.getYearFound()));
        company.setInformation(companyDto.getInformation());
        company.setProductsAndServices(companyDto.getProductsAndServices());
        company.setCompanyProfileLink(companyDto.getCompanyProfileLink());

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

    private void setOrbisCompanyProperties(Company company, OrbisCompanyImportDto companyDto) {
        setCommonCompanyProperties(company, companyDto);

        company.setVATNumber(companyDto.getVATNumber());
        company.setBvDIdNumber(companyDto.getBvDIdNumber());
        company.setISOCountryCode(companyDto.getISOCountryCode());
        company.setNaceRevMainSection(companyDto.getNaceRevMainSection());
        company.setNaceRevCoreCode(getPropertyValueAsInteger(companyDto.getNaceRevCoreCode()));
        company.setConsolidationCode(companyDto.getConsolidationCode());
        company.setOperatingIncome(companyDto.getOperatingIncome());
        company.setEmployeesCount(getPropertyValueAsInteger(companyDto.getEmployeesCount()));
        company.setAddress(companyDto.getAddress());
        company.setJobDescription(companyDto.getJobDescription());
        company.setStandardizedLegalForm(companyDto.getStandardizedLegalForm());
        company.setManagersCount(getPropertyValueAsInteger(companyDto.getManagersCount()));
        company.setCorporationCompaniesCount(getPropertyValueAsInteger(companyDto.getCorporationCompaniesCount()));
        company.setSubsidiariesCount(getPropertyValueAsInteger(companyDto.getSubsidiariesCount()));
    }

    private void setCommonCompanyProperties(Company company, CompanyImportDto companyDto) {
        company.addEmail(companyDto.getCompanyEmail());
        company.setName(companyDto.getName());
        company.setWebsite(companyDto.getWebsite());
        company.setPostcode(getPropertyValueAsInteger(companyDto.getPostcode()));
        company.setCity(companyDto.getCity());
        company.setCountry(companyDto.getCountry());
        company.setCompanyPhone(companyDto.getCompanyPhone());
    }

    private void setEmployeeProperties(EmployeeImportDto employeeDto, Employee employee) {
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
        if (!file.delete()) {
            log.warn("Failed to delete {}", file.getName());
        }
    }

    private Map<String, Company> getCompaniesMap(List<String> companyWebsites) {
        return companyRepository.findAllByWebsiteIn(companyWebsites).stream()
                .collect(Collectors.toMap(Company::getWebsite, c -> c));
    }

    private Integer getPropertyValueAsInteger(String property) {
        if (property == null) {
            return null;
        }
        try {
            return Double.valueOf(property).intValue();
        } catch (NumberFormatException e) {
            Pattern pattern = Pattern.compile(EXTRACT_INTEGER_REGEX);
            Matcher matcher = pattern.matcher(property);
            return matcher.find() ? Double.valueOf(matcher.group(0)).intValue() : null;
        }
    }
}