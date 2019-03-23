package softuni.aggregator.service.excel.writer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeesExportDto extends ExcelExportDto {

    private String companyName;
    private String fullName;
    private String position;
    private String email;
    private Integer hunterIoScore;
}
