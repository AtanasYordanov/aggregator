package softuni.aggregator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import softuni.aggregator.domain.entities.*;
import softuni.aggregator.domain.model.vo.ImportListVO;
import softuni.aggregator.domain.repository.*;
import softuni.aggregator.service.*;
import softuni.aggregator.service.excel.reader.ExcelReader;
import softuni.aggregator.service.excel.reader.imports.ImportType;
import softuni.aggregator.service.excel.reader.model.*;
import org.springframework.transaction.annotation.Transactional;
import softuni.aggregator.utils.performance.PerformanceUtils;
import softuni.aggregator.web.exceptions.ServiceException;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ImportServiceImpl implements ImportService {

    private static final String EXTRACT_INTEGER_REGEX = "\\d+";

    private final ImportRepository importRepository;
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final MinorIndustryService minorIndustryService;
    private final MajorIndustryService majorIndustryService;
    private final ServletContext servletContext;
    private final ExcelReader excelReader;
    private final ModelMapper mapper;

    @Autowired
    public ImportServiceImpl(CompanyService companyService, EmployeeService employeeService,
                             MinorIndustryService minorIndustryService,
                             MajorIndustryService majorIndustryService, ImportRepository importRepository,
                             ServletContext servletContext, ExcelReader excelReader, ModelMapper mapper) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.minorIndustryService = minorIndustryService;
        this.majorIndustryService = majorIndustryService;
        this.importRepository = importRepository;
        this.servletContext = servletContext;
        this.excelReader = excelReader;
        this.mapper = mapper;
    }

    @Override
    public List<ImportListVO> getImportsPage(Pageable pageable, User user) {
        return importRepository.findAllByUser(user, pageable).stream()
                .map(i -> {
                    ImportListVO importVO = mapper.map(i, ImportListVO.class);
                    importVO.setUserEmail(i.getUser().getEmail());
                    return importVO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public long getImportsCount(User user) {
        return importRepository.countByUser(user);
    }

    @Override
    public int importCompaniesFromXing(User user, MultipartFile multipartFile) {
        File file = saveTempFile(multipartFile);
        List<XingCompanyImportDto> data = excelReader.readExcel(file.getAbsolutePath(), ImportType.XING_COMPANIES);
        deleteFile(file);

        Map<String, MajorIndustry> majorIndustriesMap = majorIndustryService.getAllIndustriesByName();
        Map<String, MinorIndustry> minorIndustriesMap = minorIndustryService.getAllIndustriesByName();

        Map<String, Company> companies = getCompaniesMap(data);

        int existingCompaniesCount = companies.size();
        for (XingCompanyImportDto companyDto : data) {
            if (companyDto.getWebsite() != null && !companyDto.getWebsite().isBlank()) {
                Company company = companies.getOrDefault(companyDto.getWebsite(), new Company());
                setXingCompanyProperties(company, companyDto, majorIndustriesMap, minorIndustriesMap);
                companies.putIfAbsent(company.getWebsite(), company);
            }
        }

        minorIndustryService.saveAll(minorIndustriesMap.values());
        companyService.saveCompanies(companies.values());

        int newEntries = companies.size() - existingCompaniesCount;
        importRepository.save(new Import(user, ImportType.XING_COMPANIES, companies.size(), newEntries));

        log.info(String.format("Successfully imported %s companies from XING.", companies.size()));
        return companies.size();
    }

    @Override
    public int importCompaniesFromOrbis(User user, MultipartFile multipartFile) {
        File file = saveTempFile(multipartFile);
        List<OrbisCompanyImportDto> data = excelReader.readExcel(file.getAbsolutePath(), ImportType.ORBIS_COMPANIES);
        deleteFile(file);

        Map<String, Company> companies = getCompaniesMap(data);

        int existingCompaniesCount = companies.size();
        for (OrbisCompanyImportDto companyDto : data) {
            if (companyDto.getWebsite() != null && !companyDto.getWebsite().isBlank()) {
                Company company = companies.getOrDefault(companyDto.getWebsite(), new Company());
                setOrbisCompanyProperties(company, companyDto);
                companies.putIfAbsent(company.getWebsite(), company);
            }
        }

        companyService.saveCompanies(companies.values());

        int newEntries = companies.size() - existingCompaniesCount;
        importRepository.save(new Import(user, ImportType.ORBIS_COMPANIES, companies.size(), newEntries));

        log.info(String.format("Successfully imported %s companies from Orbis.", companies.size()));
        return companies.size();
    }

    @Override
    public int importEmployees(User user, MultipartFile multipartFile) {
        File file = saveTempFile(multipartFile);
        List<EmployeeImportDto> data = excelReader.readExcel(file.getAbsolutePath(), ImportType.EMPLOYEES);
        deleteFile(file);

        List<String> emails = data.stream()
                .map(EmployeeImportDto::getEmail)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<String> companyWebsites = data.stream()
                .map(EmployeeImportDto::getCompanyWebsite)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<String, Employee> employees = employeeService.getEmployeesByEmail(emails);
        Map<String, Company> companies = companyService.getCompaniesByWebsite(companyWebsites);

        int existingCompaniesCount = employees.size();

        for (EmployeeImportDto employeeDto : data) {
            Employee employee = employees.getOrDefault(employeeDto.getEmail(), new Employee());
            setEmployeeProperties(employeeDto, employee, companies);
            employees.putIfAbsent(employee.getEmail(), employee);
        }

        employeeService.saveEmployees(employees.values());

        int newEntries = employees.size() - existingCompaniesCount;
        importRepository.save(new Import(user, ImportType.EMPLOYEES, employees.size(), newEntries));

        log.info(String.format("Successfully imported %s employees.", employees.size()));
        return employees.size();
    }

    private Map<String, Company> getCompaniesMap(List<? extends CompanyImportDto> data) {
        List<String> companyWebsites = data.stream()
                .map(CompanyImportDto::getWebsite)
                .distinct()
                .collect(Collectors.toList());

        return companyService.getCompaniesByWebsite(companyWebsites);
    }

    private void setXingCompanyProperties(Company company, XingCompanyImportDto companyDto, Map<String,
            MajorIndustry> majorIndustryMap, Map<String, MinorIndustry> minorIndustryMap) {
        setCommonCompanyProperties(company, companyDto);

        company.setEmployeesRange(companyDto.getEmployeesRange());
        company.setEmployeesCount(getPropertyValueAsInteger(companyDto.getEmployeesListed()));
        company.setEmployeesPage(companyDto.getEmployeesPage());
        company.setStreet(companyDto.getStreet());
        company.setFax(companyDto.getFax());
        company.setXingProfileLink(companyDto.getXingProfileLink());
        company.setYearFound(getPropertyValueAsInteger(companyDto.getYearFound()));
        company.setInformation(companyDto.getInformation());
        company.setProductsAndServices(companyDto.getProductsAndServices());
        company.setXingProfileLink(companyDto.getXingProfileLink());

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

    private void setEmployeeProperties(EmployeeImportDto employeeDto, Employee employee, Map<String, Company> companies) {
        Company company = employee.getCompany();
        if (company == null) {
            company = companies.get(employeeDto.getCompanyWebsite());
        }

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

        File file;
        try {
            file = new File(filePath);
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new ServiceException("Failed to save excel file.");
        }
        return file;
    }

    private void deleteFile(File file) {
        if (!file.delete()) {
            log.warn("Failed to delete {}", file.getName());
        }
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