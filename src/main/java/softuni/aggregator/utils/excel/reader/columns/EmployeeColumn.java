package softuni.aggregator.utils.excel.reader.columns;

import softuni.aggregator.utils.excel.reader.model.EmployeeExcelDto;

import java.util.function.BiConsumer;

public enum EmployeeColumn implements ReadExcelColumn<EmployeeExcelDto> {

    COMPANY_NAME(EmployeeExcelDto::setCompanyName),
    FULL_NAME(EmployeeExcelDto::setFullName),
    POSITION(EmployeeExcelDto::setPosition),
    EMAIL(EmployeeExcelDto::setEmail),
    HUNTER_IO_SCORE(EmployeeExcelDto::setHunterIoScore);

    private BiConsumer<EmployeeExcelDto, String> setter;

    EmployeeColumn(BiConsumer<EmployeeExcelDto, String> setter) {
        this.setter = setter;
    }

    @Override
    public BiConsumer<EmployeeExcelDto, String> getSetter() {
        return setter;
    }
}
