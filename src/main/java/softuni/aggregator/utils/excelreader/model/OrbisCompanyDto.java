package softuni.aggregator.utils.excelreader.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrbisCompanyDto extends BaseExcelDto {

    private String name;
    private String website;
    private String VATNumber;
    private String BvDIdNumber;
    private String ISOCountryCode;
    private String naceRevMainSection;
    private String naceRevCoreCode;
    private String consolidationCode;
    private String operatingIncome;
    private String employeesCount;
    private String address;
    private String postcode;
    private String location;
    private String country;
    private String phoneNumber;
    private String companyEmail;
    private String jobDescription;
    private String standardizedLegalForm;
    private String managersCount;
    private String corporationCompaniesCount;
    private String subsidiariesCount;
}
