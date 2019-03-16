package softuni.aggregator.utils.excelreader.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeExcelDto extends BaseExcelDto {

    private String companyName;
    private String fullName;
    private String position;
    private String email;
    private String hunterIoScore;
}
