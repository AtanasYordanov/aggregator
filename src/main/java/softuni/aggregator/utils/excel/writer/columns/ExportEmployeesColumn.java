package softuni.aggregator.utils.excel.writer.columns;

import softuni.aggregator.utils.excel.writer.model.WriteEmployeesExcelDto;

import java.util.function.Function;

public enum ExportEmployeesColumn implements WriteExcelColumn<WriteEmployeesExcelDto> {

    COMPANY_NAME("Company name", WriteEmployeesExcelDto::getCompanyName),
    FULL_NAME("Full name", WriteEmployeesExcelDto::getFullName),
    POSITION("Position", WriteEmployeesExcelDto::getPosition),
    EMAIL("Email", WriteEmployeesExcelDto::getEmail),
    HUNTER_IO_SCORE("Hunter.io Score", WriteEmployeesExcelDto::getHunterIoScore);

    private String columnName;
    private Function<WriteEmployeesExcelDto, ?> getter;

    ExportEmployeesColumn(String columnName, Function<WriteEmployeesExcelDto, ?>  getter) {
        this.columnName = columnName;
        this.getter = getter;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public Function<WriteEmployeesExcelDto, ?> getGetter() {
        return getter;
    }
}
