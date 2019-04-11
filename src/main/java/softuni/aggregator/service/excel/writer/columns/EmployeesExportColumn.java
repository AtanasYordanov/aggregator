package softuni.aggregator.service.excel.writer.columns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import softuni.aggregator.service.excel.writer.model.EmployeeExportDto;

import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum EmployeesExportColumn implements WriteExcelColumn<EmployeeExportDto> {

    COMPANY_NAME("Company name", EmployeeExportDto::getCompanyName),
    FULL_NAME("Full name", EmployeeExportDto::getFullName),
    POSITION("Position", EmployeeExportDto::getPosition),
    EMAIL("Email", EmployeeExportDto::getEmail),
    HUNTER_IO_SCORE("Hunter.io Score", EmployeeExportDto::getHunterIoScore);

    private String columnName;
    private Function<EmployeeExportDto, ?> getter;
}
