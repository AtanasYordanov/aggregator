package softuni.aggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.MajorIndustry;
import softuni.aggregator.domain.entities.MinorIndustry;
import softuni.aggregator.domain.repository.CompanyRepository;
import softuni.aggregator.service.api.CompanyService;
import softuni.aggregator.service.excel.writer.model.CompaniesExportExcelDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<CompaniesExportExcelDto> getCompaniesForExport() {
        return mapToExcelDto(companyRepository.findAll());
    }

    private List<CompaniesExportExcelDto> mapToExcelDto(List<Company> companies) {
        List<CompaniesExportExcelDto> companyDtos = new ArrayList<>();
        for (Company company : companies) {
            CompaniesExportExcelDto companyDto = new CompaniesExportExcelDto();

            companyDto.setName(company.getName());
            companyDto.setWebsite(company.getWebsite());
            companyDto.setPostcode(company.getPostcode());
            companyDto.setCity(company.getCity());
            companyDto.setCountry(company.getCountry());
            companyDto.setCompanyPhone(company.getCompanyPhone());
            companyDto.setCompanyEmails(String.join(System.lineSeparator(),company.getCompanyEmails()));
            companyDto.setXingIndustry1(getMajorIndustry(company));
            companyDto.setXingIndustry2(getMinorIndustry(company));
            companyDto.setEmployeesRange(company.getEmployeesRange());
            companyDto.setStreet(company.getStreet());
            companyDto.setFax(company.getFax());
            companyDto.setInformation(company.getInformation());
            companyDto.setCompanyProfileLink(company.getCompanyProfileLink());
            companyDto.setYearFound(company.getYearFound());
            companyDto.setProductsAndServices(company.getProductsAndServices());
            companyDto.setVATNumber(company.getVATNumber());
            companyDto.setBvDIdNumber(company.getBvDIdNumber());
            companyDto.setISOCountryCode(company.getISOCountryCode());
            companyDto.setNaceRevMainSection(company.getNaceRevMainSection());
            companyDto.setNaceRevCoreCode(company.getNaceRevCoreCode());
            companyDto.setConsolidationCode(company.getConsolidationCode());
            companyDto.setOperatingIncome(company.getOperatingIncome());
            companyDto.setEmployeesCount(company.getEmployeesCount());
            companyDto.setAddress(company.getAddress());
            companyDto.setJobDescription(company.getJobDescription());
            companyDto.setStandardizedLegalForm(company.getStandardizedLegalForm());
            companyDto.setManagersCount(company.getManagersCount());
            companyDto.setCorporationCompaniesCount(company.getCorporationCompaniesCount());
            companyDto.setSubsidiariesCount(company.getSubsidiariesCount());

            companyDtos.add(companyDto);
        }
        return companyDtos;
    }

    private String getMajorIndustry(Company company) {
        MinorIndustry industry = company.getIndustry();
        if (industry == null) {
            return null;
        }
        MajorIndustry majorIndustry = industry.getMajorIndustry();
        return majorIndustry != null ?  majorIndustry.getName() : null;
    }

    private String getMinorIndustry(Company company) {
        MinorIndustry industry = company.getIndustry();
        return industry != null ?  industry.getName() : null;
    }
}
