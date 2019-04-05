package softuni.aggregator.service.excel.reader.columns;

import lombok.AllArgsConstructor;
import softuni.aggregator.service.excel.reader.model.EmployeeImportDto;

import java.util.function.BiConsumer;

@AllArgsConstructor
public enum EmployeesImportColumn implements ReadExcelColumn {

    COMPANY_NAME("Company name", EmployeeImportDto::setCompanyName),
    FULL_NAME("Full name", EmployeeImportDto::setFullName),
    POSITION("Position", EmployeeImportDto::setPosition),
    EMAIL("CEO Email", EmployeeImportDto::setEmail),
    HUNTER_IO_SCORE("Hunter.io Score", EmployeeImportDto::setHunterIoScore);

    private String columnName;
    private BiConsumer<EmployeeImportDto, String> setter;

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public BiConsumer<EmployeeImportDto, String> getSetter() {
        return setter;
    }
}
