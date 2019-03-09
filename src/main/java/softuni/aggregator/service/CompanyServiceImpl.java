package softuni.aggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Company;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Company> companies = new ArrayList<>();

        Map<String, MajorIndustry> majorIndustryMap = new HashMap<>();
        Map<String, MinorIndustry> minorIndustryMap = new HashMap<>();

        for (XingCompanyDto companyDto : data) {
            Company company = companyRepository.findByWebsite(companyDto.getWebsite())
                    .orElse(new Company());
            setProperties(companyDto, company, majorIndustryMap, minorIndustryMap);
            companies.add(company);
        }

        companyRepository.saveAll(companies);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveCompanyFromOrbis() {
        ExcelReader reader = new OrbisExcelReader();

        String excelFilePath = "src\\main\\resources\\static\\ORBIS sample.xlsx";

        List<OrbisCompanyDto> data = reader.readExcel(excelFilePath);
        System.out.println();
    }

    private void setProperties(XingCompanyDto companyDto, Company company, Map<String,
            MajorIndustry> majorIndustryMap, Map<String, MinorIndustry> minorIndustryMap) {
        company.setName(companyDto.getName());
        company.setWebsite(companyDto.getWebsite());
        company.setEmployeesRange(companyDto.getEmployeesRange());
        company.setStreet(companyDto.getStreet());
        company.setPostcode(companyDto.getPostcode() != null
                ? Double.valueOf(companyDto.getPostcode()).intValue()
                : null);
        company.setCountry(companyDto.getCountry());
        company.setCity(companyDto.getCity());
        company.setCompanyPhone(companyDto.getCompanyPhone());
        company.setFax(companyDto.getFax());
        company.setCompanyEmail(companyDto.getCompanyEmail());
        company.setCompanyProfileLink(companyDto.getCompanyProfileLink());
        company.setYearFound(companyDto.getYearFound() != null ?
                Double.valueOf(companyDto.getYearFound()).intValue()
                : null);
        company.setInformation(companyDto.getInformation());
        company.setCompanyProfileLink(companyDto.getCompanyProfileLink());

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
}
