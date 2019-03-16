package softuni.aggregator.utils.excelreader.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CompanyExcelDto extends BaseExcelDto {
    private String name;
    private String website;
    private String postcode;
    private String city;
    private String country;
    private String companyPhone;
    private String companyEmail;
}
