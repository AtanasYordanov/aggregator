package softuni.aggregator.service.excel.writer.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class EmployeeWithCompanyExportDto extends ExcelExportDto {

    private EmployeeExportDto employeeExportDto;
    private CompanyExportDto companyExportDto;
}
