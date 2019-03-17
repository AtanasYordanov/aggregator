package softuni.aggregator.utils.excel.reader.columns;

import softuni.aggregator.utils.excel.reader.model.EmployeeExcelDto;

import java.util.function.BiConsumer;

public enum EmployeeColumn implements ReadExcelColumn<EmployeeExcelDto> {

    COMPANY_NAME("Company name", EmployeeExcelDto::setCompanyName),
    FULL_NAME("Full name", EmployeeExcelDto::setFullName),
    POSITION("Position", EmployeeExcelDto::setPosition),
    EMAIL("CEO Email", EmployeeExcelDto::setEmail),
    HUNTER_IO_SCORE("Hunter.io Score", EmployeeExcelDto::setHunterIoScore);

    private String columnName;
    private BiConsumer<EmployeeExcelDto, String> setter;

    EmployeeColumn(String columnName, BiConsumer<EmployeeExcelDto, String> setter) {
        this.columnName = columnName;
        this.setter = setter;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public BiConsumer<EmployeeExcelDto, String> getSetter() {
        return setter;
    }
}
