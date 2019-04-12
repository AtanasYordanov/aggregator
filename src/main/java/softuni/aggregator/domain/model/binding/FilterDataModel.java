package softuni.aggregator.domain.model.binding;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterDataModel {

    private String industry;
    private Integer minEmployeesCount;
    private Integer maxEmployeesCount;
    private Boolean includeCompaniesWithNoEmployeeData;
    private Integer yearFound;
    private String country;
    private String city;
}
