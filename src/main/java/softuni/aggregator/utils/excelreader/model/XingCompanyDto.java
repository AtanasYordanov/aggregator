package softuni.aggregator.utils.excelreader.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XingCompanyDto extends BaseExcelDto {

    private String xingIndustry1;
    private String xingIndustry2;
    private String name;
    private String website;
    private String employeesRange;
    private String street;
    private String postcode;
    private String country;
    private String city;
    private String companyPhone;
    private String fax;
    private String companyEmail;
    private String information;
    private String employeesListed;
    private String employeesPage;
    private String companyProfileLink;
    private String yearFound;
}
