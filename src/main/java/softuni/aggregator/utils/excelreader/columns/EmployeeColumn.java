package softuni.aggregator.utils.excelreader.columns;

import softuni.aggregator.utils.excelreader.model.EmployeeExcelDto;

import java.util.function.BiConsumer;

public enum EmployeeColumn implements ExcelColumn<EmployeeExcelDto> {

    COMPANY_NAME(EmployeeExcelDto::setCompanyName),
    FULL_NAME(EmployeeExcelDto::setFullName),
    POSITION(EmployeeExcelDto::setPosition),
    EMAIL(EmployeeExcelDto::setEmail),
    HUNTER_IO_SCORE(EmployeeExcelDto::setHunterIoScore);

    EmployeeColumn(BiConsumer<EmployeeExcelDto, String> setter) {
        this.setter = setter;
    }

    private BiConsumer<EmployeeExcelDto, String> setter;

    @Override
    public BiConsumer<EmployeeExcelDto, String> getSetter() {
        return setter;
    }
}
