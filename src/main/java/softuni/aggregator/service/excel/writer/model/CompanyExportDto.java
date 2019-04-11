package softuni.aggregator.service.excel.writer.model;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.MajorIndustry;
import softuni.aggregator.domain.entities.MinorIndustry;

@Getter
@Setter
public class CompanyExportDto extends ExcelExportDto {

    private String name;
    private String website;
    private Integer postcode;
    private String city;
    private String country;
    private String companyPhone;
    private String companyEmails;
    private String xingIndustry1;
    private String xingIndustry2;
    private String employeesRange;
    private String street;
    private String fax;
    private String information;
    private String employeesPage;
    private String xingProfileLink;
    private Integer yearFound;
    private String productsAndServices;
    private String VATNumber;
    private String BvDIdNumber;
    private String ISOCountryCode;
    private String naceRevMainSection;
    private Integer naceRevCoreCode;
    private String consolidationCode;
    private String operatingIncome;
    private Integer employeesCount;
    private String address;
    private String jobDescription;
    private String standardizedLegalForm;
    private Integer managersCount;
    private Integer corporationCompaniesCount;
    private Integer subsidiariesCount;

    public CompanyExportDto(Company company) {
        if (company == null) {
            return;
        }

        setName(company.getName());
        setWebsite(company.getWebsite());
        setPostcode(company.getPostcode());
        setCity(company.getCity());
        setCountry(company.getCountry());
        setCompanyPhone(company.getCompanyPhone());
        setCompanyEmails(String.join(System.lineSeparator(), company.getCompanyEmails()));
        setXingIndustry1(getMajorIndustry(company));
        setXingIndustry2(getMinorIndustry(company));
        setEmployeesRange(company.getEmployeesRange());
        setEmployeesPage(company.getEmployeesPage());
        setStreet(company.getStreet());
        setFax(company.getFax());
        setInformation(company.getInformation());
        setXingProfileLink(company.getXingProfileLink());
        setYearFound(company.getYearFound());
        setProductsAndServices(company.getProductsAndServices());
        setVATNumber(company.getVATNumber());
        setBvDIdNumber(company.getBvDIdNumber());
        setISOCountryCode(company.getISOCountryCode());
        setNaceRevMainSection(company.getNaceRevMainSection());
        setNaceRevCoreCode(company.getNaceRevCoreCode());
        setConsolidationCode(company.getConsolidationCode());
        setOperatingIncome(company.getOperatingIncome());
        setEmployeesCount(company.getEmployeesCount());
        setAddress(company.getAddress());
        setJobDescription(company.getJobDescription());
        setStandardizedLegalForm(company.getStandardizedLegalForm());
        setManagersCount(company.getManagersCount());
        setCorporationCompaniesCount(company.getCorporationCompaniesCount());
        setSubsidiariesCount(company.getSubsidiariesCount());
    }

    private String getMajorIndustry(Company company) {
        MinorIndustry industry = company.getIndustry();
        if (industry == null) {
            return null;
        }
        MajorIndustry majorIndustry = industry.getMajorIndustry();
        return majorIndustry != null ? majorIndustry.getName() : null;
    }

    private String getMinorIndustry(Company company) {
        MinorIndustry industry = company.getIndustry();
        return industry != null ? industry.getName() : null;
    }
}
