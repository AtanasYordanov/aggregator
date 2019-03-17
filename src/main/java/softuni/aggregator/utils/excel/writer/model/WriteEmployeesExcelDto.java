package softuni.aggregator.utils.excel.writer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WriteEmployeesExcelDto extends WriteExcelDto {

    private String companyName;
    private String fullName;
    private String position;
    private String email;
    private Integer hunterIoScore;
}
