package softuni.aggregator.utils.excel.reader.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeExcelDto extends ReadExcelDto {

    private String companyName;
    private String fullName;
    private String position;
    private String email;
    private String hunterIoScore;
}
