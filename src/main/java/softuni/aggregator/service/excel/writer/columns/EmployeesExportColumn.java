package softuni.aggregator.service.excel.writer.columns;

import softuni.aggregator.service.excel.writer.model.EmployeesExportExcelDto;

import java.util.function.Function;

public enum EmployeesExportColumn implements WriteExcelColumn<EmployeesExportExcelDto> {

    COMPANY_NAME("Company name", EmployeesExportExcelDto::getCompanyName),
    FULL_NAME("Full name", EmployeesExportExcelDto::getFullName),
    POSITION("Position", EmployeesExportExcelDto::getPosition),
    EMAIL("Email", EmployeesExportExcelDto::getEmail),
    HUNTER_IO_SCORE("Hunter.io Score", EmployeesExportExcelDto::getHunterIoScore);

    private String columnName;
    private Function<EmployeesExportExcelDto, ?> getter;

    EmployeesExportColumn(String columnName, Function<EmployeesExportExcelDto, ?> getter) {
        this.columnName = columnName;
        this.getter = getter;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public Function<EmployeesExportExcelDto, ?> getGetter() {
        return getter;
    }
}
