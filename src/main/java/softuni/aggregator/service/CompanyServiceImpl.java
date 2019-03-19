package softuni.aggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.CompanyDetails;
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

            CompanyDetails companyDetails = company.getCompanyDetails();

            companyDto.setName(company.getName());
            companyDto.setWebsite(company.getWebsite());
            companyDto.setPostcode(companyDetails.getPostcode());
            companyDto.setCity(companyDetails.getCity());
            companyDto.setCountry(companyDetails.getCountry());
            companyDto.setCompanyPhone(companyDetails.getCompanyPhone());
            companyDto.setCompanyEmails(String.join(System.lineSeparator(),company.getCompanyEmails()));
            companyDto.setXingIndustry1(getMajorIndustry(company));
            companyDto.setXingIndustry2(getMinorIndustry(company));
            companyDto.setEmployeesRange(companyDetails.getEmployeesRange());
            companyDto.setStreet(companyDetails.getStreet());
            companyDto.setFax(companyDetails.getFax());
            companyDto.setInformation(companyDetails.getInformation());
            companyDto.setCompanyProfileLink(companyDetails.getCompanyProfileLink());
            companyDto.setYearFound(companyDetails.getYearFound());
            companyDto.setProductsAndServices(companyDetails.getProductsAndServices());
            companyDto.setVATNumber(companyDetails.getVATNumber());
            companyDto.setBvDIdNumber(companyDetails.getBvDIdNumber());
            companyDto.setISOCountryCode(companyDetails.getISOCountryCode());
            companyDto.setNaceRevMainSection(companyDetails.getNaceRevMainSection());
            companyDto.setNaceRevCoreCode(companyDetails.getNaceRevCoreCode());
            companyDto.setConsolidationCode(companyDetails.getConsolidationCode());
            companyDto.setOperatingIncome(companyDetails.getOperatingIncome());
            companyDto.setEmployeesCount(companyDetails.getEmployeesCount());
            companyDto.setAddress(companyDetails.getAddress());
            companyDto.setJobDescription(companyDetails.getJobDescription());
            companyDto.setStandardizedLegalForm(companyDetails.getStandardizedLegalForm());
            companyDto.setManagersCount(companyDetails.getManagersCount());
            companyDto.setCorporationCompaniesCount(companyDetails.getCorporationCompaniesCount());
            companyDto.setSubsidiariesCount(companyDetails.getSubsidiariesCount());

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
