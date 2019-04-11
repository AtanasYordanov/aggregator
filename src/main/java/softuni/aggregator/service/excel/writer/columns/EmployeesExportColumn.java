package softuni.aggregator.service.excel.writer.columns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import softuni.aggregator.service.excel.writer.model.EmployeesExportDto;

import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum EmployeesExportColumn implements WriteExcelColumn<EmployeesExportDto> {

    COMPANY_NAME("Company name", EmployeesExportDto::getCompanyName),
    FULL_NAME("Full name", EmployeesExportDto::getFullName),
    POSITION("Position", EmployeesExportDto::getPosition),
    EMAIL("Email", EmployeesExportDto::getEmail),
    HUNTER_IO_SCORE("Hunter.io Score", EmployeesExportDto::getHunterIoScore);

    private String columnName;
    private Function<EmployeesExportDto, ?> getter;
}
