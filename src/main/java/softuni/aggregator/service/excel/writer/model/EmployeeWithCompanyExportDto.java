package softuni.aggregator.service.excel.writer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeWithCompanyExportDto extends ExcelExportDto {

    private EmployeeExportDto employeeExportDto;
    private CompanyExportDto companyExportDto;
}
