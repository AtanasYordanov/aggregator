package softuni.aggregator.service.excel.reader.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XingCompanyImportDto extends CompanyImportDto {

    private String xingIndustry1;
    private String xingIndustry2;
    private String employeesRange;
    private String street;
    private String fax;
    private String information;
    private String employeesListed;
    private String employeesPage;
    private String xingProfileLink;
    private String yearFound;
    private String productsAndServices;
}
