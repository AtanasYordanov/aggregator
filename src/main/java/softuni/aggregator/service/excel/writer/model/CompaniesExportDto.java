package softuni.aggregator.service.excel.writer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompaniesExportDto extends ExcelExportDto {

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
}
