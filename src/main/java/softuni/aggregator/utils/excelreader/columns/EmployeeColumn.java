package softuni.aggregator.utils.excelreader.columns;

import softuni.aggregator.utils.excelreader.model.EmployeesExcelDto;

import java.util.function.BiConsumer;

public enum EmployeeColumn implements ExcelColumn<EmployeesExcelDto> {

    COMPANY_NAME(EmployeesExcelDto::setCompanyName),
    FULL_NAME(EmployeesExcelDto::setFullName),
    POSITION(EmployeesExcelDto::setPosition),
    EMAIL(EmployeesExcelDto::setEmail),
    HUNTER_IO_SCORE(EmployeesExcelDto::setHunterIoScore);

    EmployeeColumn(BiConsumer<EmployeesExcelDto, String> setter) {
        this.setter = setter;
    }

    private BiConsumer<EmployeesExcelDto, String> setter;

    @Override
    public BiConsumer<EmployeesExcelDto, String> getSetter() {
        return setter;
    }
}
