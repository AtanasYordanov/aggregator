package softuni.aggregator.service.excel.reader.columns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import softuni.aggregator.service.excel.reader.model.EmployeeImportDto;

import java.util.function.BiConsumer;

@Getter
@AllArgsConstructor
public enum EmployeesImportColumn implements ReadExcelColumn {

    COMPANY_NAME("Company name", EmployeeImportDto::setCompanyName),
    COMPANY_WEBSITE("Company website", EmployeeImportDto::setCompanyWebsite),
    FULL_NAME("Full name", EmployeeImportDto::setFullName),
    POSITION("Position", EmployeeImportDto::setPosition),
    EMAIL("CEO Email", EmployeeImportDto::setEmail),
    HUNTER_IO_SCORE("Hunter.io Score", EmployeeImportDto::setHunterIoScore);

    private String columnName;
    private BiConsumer<EmployeeImportDto, String> setter;
}
