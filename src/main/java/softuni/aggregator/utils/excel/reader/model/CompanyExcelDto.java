package softuni.aggregator.utils.excel.reader.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CompanyExcelDto extends ReadExcelDto {

    private String name;
    private String website;
    private String postcode;
    private String city;
    private String country;
    private String companyPhone;
    private String companyEmail;
}
