package softuni.aggregator.service.excel.writer.columns;

import softuni.aggregator.service.excel.writer.model.EmployeesExportDto;

import java.util.function.Function;

public enum EmployeesExportColumn implements WriteExcelColumn<EmployeesExportDto> {

    COMPANY_NAME("Company name", EmployeesExportDto::getCompanyName),
    FULL_NAME("Full name", EmployeesExportDto::getFullName),
    POSITION("Position", EmployeesExportDto::getPosition),
    EMAIL("Email", EmployeesExportDto::getEmail),
    HUNTER_IO_SCORE("Hunter.io Score", EmployeesExportDto::getHunterIoScore);

    private String columnName;
    private Function<EmployeesExportDto, ?> getter;

    EmployeesExportColumn(String columnName, Function<EmployeesExportDto, ?> getter) {
        this.columnName = columnName;
        this.getter = getter;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public Function<EmployeesExportDto, ?> getGetter() {
        return getter;
    }
}
