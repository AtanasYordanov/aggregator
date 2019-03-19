package softuni.aggregator.service.excel.reader.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeExcelDto extends ImportExcelDto {

    private String companyName;
    private String fullName;
    private String position;
    private String email;
    private String hunterIoScore;
}
