package softuni.aggregator.domain.model.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDetailsVO {

    private String fullName;
    private String position;
    private String email;
    private Integer hunterIoScore;
    private EmployeeCompanyVO company;
}
