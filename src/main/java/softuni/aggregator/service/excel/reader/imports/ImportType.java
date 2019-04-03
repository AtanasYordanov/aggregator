package softuni.aggregator.service.excel.reader.imports;

import softuni.aggregator.service.excel.reader.columns.EmployeesImportColumn;
import softuni.aggregator.service.excel.reader.columns.OrbisImportColumn;
import softuni.aggregator.service.excel.reader.columns.ReadExcelColumn;
import softuni.aggregator.service.excel.reader.columns.XingImportColumn;
import softuni.aggregator.service.excel.reader.model.EmployeeImportDto;
import softuni.aggregator.service.excel.reader.model.ExcelImportDto;
import softuni.aggregator.service.excel.reader.model.OrbisCompanyImportDto;
import softuni.aggregator.service.excel.reader.model.XingCompanyImportDto;

import java.util.function.Supplier;

public enum ImportType {
    EMPLOYEES(EmployeesImportColumn.values(), EmployeeImportDto::new),
    XING_COMPANIES(XingImportColumn.values(), XingCompanyImportDto::new),
    ORBIS_COMPANIES(OrbisImportColumn.values(), OrbisCompanyImportDto::new);

    private ReadExcelColumn[] columns;
    private Supplier<ExcelImportDto> instanceCreator;

    ImportType(ReadExcelColumn[] columns, Supplier<ExcelImportDto> instanceCreator) {
        this.columns = columns;
        this.instanceCreator = instanceCreator;
    }

    public ExcelImportDto createInstance() {
        return instanceCreator.get();
    }

    public ReadExcelColumn[] getColumns() {
        return columns;
    }
}
