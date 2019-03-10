package softuni.aggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.CompanyDetails;
import softuni.aggregator.domain.entities.MajorIndustry;
import softuni.aggregator.domain.entities.MinorIndustry;
import softuni.aggregator.domain.repository.CompanyRepository;
import softuni.aggregator.domain.repository.MajorIndustryRepository;
import softuni.aggregator.domain.repository.MinorIndustryRepository;
import softuni.aggregator.service.api.CompanyService;
import softuni.aggregator.utils.excelreader.ExcelReader;
import softuni.aggregator.utils.excelreader.model.OrbisCompanyDto;
import softuni.aggregator.utils.excelreader.readers.OrbisExcelReader;
import softuni.aggregator.utils.excelreader.readers.XingExcelReader;
import softuni.aggregator.utils.excelreader.model.XingCompanyDto;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MinorIndustryRepository minorIndustryRepository;
    private final MajorIndustryRepository majorIndustryRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, MinorIndustryRepository minorIndustryRepository,
                              MajorIndustryRepository majorIndustryRepository) {
        this.companyRepository = companyRepository;
        this.minorIndustryRepository = minorIndustryRepository;
        this.majorIndustryRepository = majorIndustryRepository;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveCompanyFromXing() {
        ExcelReader reader = new XingExcelReader();

        String excelFilePath = "src\\main\\resources\\static\\XING sample.xlsx";

        List<XingCompanyDto> data = reader.readExcel(excelFilePath);
        Map<String, Company> companies = new HashMap<>();

        Map<String, MajorIndustry> majorIndustryMap = new HashMap<>();
        Map<String, MinorIndustry> minorIndustryMap = new HashMap<>();

        for (XingCompanyDto companyDto : data) {
            Company company = companyRepository.findByWebsite(companyDto.getWebsite())
                    .orElse(new Company());
            setProperties(companyDto, company, majorIndustryMap, minorIndustryMap);
            companies.putIfAbsent(company.getWebsite(), company);
        }

        companyRepository.saveAll(companies.values());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveCompanyFromOrbis() {
        ExcelReader reader = new OrbisExcelReader();

        String excelFilePath = "src\\main\\resources\\static\\ORBIS sample.xlsx";

        List<OrbisCompanyDto> data = reader.readExcel(excelFilePath);
        Map<String, Company> companies = new LinkedHashMap<>();

        for (OrbisCompanyDto companyDto : data) {
            Company company = companyRepository.findByWebsite(companyDto.getWebsite())
                    .orElse(new Company());
            setProperties(companyDto, company);
            companies.putIfAbsent(company.getWebsite(), company);
        }
        companyRepository.saveAll(companies.values());
    }

    private void setProperties(XingCompanyDto companyDto, Company company, Map<String,
            MajorIndustry> majorIndustryMap, Map<String, MinorIndustry> minorIndustryMap) {
        company.setName(companyDto.getName());
        company.setWebsite(companyDto.getWebsite());
        company.setCompanyEmail(companyDto.getCompanyEmail());

        setCompanyDetails(companyDto, company);

        String majorIndustryName = companyDto.getXingIndustry1();
        MajorIndustry majorIndustry = majorIndustryRepository.findByName(majorIndustryName)
                .orElse(majorIndustryMap.getOrDefault(majorIndustryName, new MajorIndustry(companyDto.getXingIndustry1())));

        majorIndustryMap.putIfAbsent(majorIndustryName, majorIndustry);

        String minorIndustryName = companyDto.getXingIndustry2();
        MinorIndustry minorIndustry = minorIndustryRepository.findByName(minorIndustryName)
                .orElse(minorIndustryMap.getOrDefault(minorIndustryName, new MinorIndustry(minorIndustryName, majorIndustry)));

        minorIndustryMap.putIfAbsent(minorIndustryName, minorIndustry);

        company.setIndustry(minorIndustry);
    }

    private void setProperties(OrbisCompanyDto companyDto, Company company) {
        company.setName(companyDto.getName());
        company.setWebsite(companyDto.getWebsite());
        company.setCompanyEmail(companyDto.getCompanyEmail());

        setCompanyDetails(companyDto, company);
    }

    private void setCompanyDetails(XingCompanyDto companyDto, Company company) {
        CompanyDetails companyDetails = company.getCompanyDetails();
        if (companyDetails == null) {
            companyDetails = new CompanyDetails();
        }

        companyDetails.setEmployeesRange(companyDto.getEmployeesRange());
        companyDetails.setStreet(companyDto.getStreet());
        companyDetails.setPostcode(getPropertyValueAsInteger(companyDto.getPostcode()));
        companyDetails.setCountry(companyDto.getCountry());
        companyDetails.setCity(companyDto.getCity());
        companyDetails.setCompanyPhone(companyDto.getCompanyPhone());
        companyDetails.setFax(companyDto.getFax());
        companyDetails.setCompanyProfileLink(companyDto.getCompanyProfileLink());
        companyDetails.setYearFound(getPropertyValueAsInteger(companyDto.getYearFound()));
        companyDetails.setInformation(companyDto.getInformation());
        companyDetails.setCompanyProfileLink(companyDto.getCompanyProfileLink());

        company.setCompanyDetails(companyDetails);
    }

    private void setCompanyDetails(OrbisCompanyDto companyDto, Company company) {
        CompanyDetails companyDetails = company.getCompanyDetails();
        if (companyDetails == null) {
            companyDetails = new CompanyDetails();
        }

        companyDetails.setEmployeesCount(getPropertyValueAsInteger(companyDto.getManagersCount()));
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

    private Integer getPropertyValueAsInteger(String property) {
        return property != null ? Double.valueOf(property).intValue() : null;
    }
}
