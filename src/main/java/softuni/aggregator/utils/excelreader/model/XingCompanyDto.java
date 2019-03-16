package softuni.aggregator.utils.excelreader.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XingCompanyDto extends CompanyExcelDto {

    private String xingIndustry1;
    private String xingIndustry2;
    private String employeesRange;
    private String street;
    private String fax;
    private String information;

    // TODO
    private String employeesListed;
    private String employeesPage;
    ////////

    private String companyProfileLink;
    private String yearFound;
}
