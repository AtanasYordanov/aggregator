package softuni.aggregator.utils.excelreader.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrbisCompanyDto extends CompanyExcelDto {

    private String VATNumber;
    private String BvDIdNumber;
    private String ISOCountryCode;
    private String naceRevMainSection;
    private String naceRevCoreCode;
    private String consolidationCode;
    private String operatingIncome;
    private String employeesCount;
    private String address;
    private String jobDescription;
    private String standardizedLegalForm;
    private String managersCount;
    private String corporationCompaniesCount;
    private String subsidiariesCount;
}
