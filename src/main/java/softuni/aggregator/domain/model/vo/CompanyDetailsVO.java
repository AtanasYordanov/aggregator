package softuni.aggregator.domain.model.vo;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.domain.entities.SubIndustry;

import java.util.List;

@Getter
@Setter
public class CompanyDetailsVO {

    private Long id;
    private String name;
    private String website;
    private String companyEmails;
    private String industry;
    private String employeesRange;
    private String street;
    private Integer postcode;
    private String country;
    private String city;
    private String companyPhone;
    private String fax;
    private String xingProfileLink;
    private Integer yearFound;
    private String information;
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
    private List<EmployeeListVO> employees;

    public void setIndustry(SubIndustry industry) {
        this.industry = industry != null ? industry.getName() : null;
    }

    public void setCompanyEmails(List<String> companyEmails) {
        this.companyEmails = String.join(", ", companyEmails);
    }
}
