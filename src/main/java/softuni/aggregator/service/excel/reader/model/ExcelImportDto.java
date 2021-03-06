package softuni.aggregator.service.excel.reader.model;

import softuni.aggregator.service.excel.reader.columns.ReadExcelColumn;

public abstract class ExcelImportDto {

    @SuppressWarnings("unchecked")
    public void setProperty(ReadExcelColumn column, String property) {
        column.getSetter().accept(this, property);
    }
}
