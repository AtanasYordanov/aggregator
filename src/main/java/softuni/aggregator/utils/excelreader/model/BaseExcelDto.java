package softuni.aggregator.utils.excelreader.model;

import softuni.aggregator.utils.excelreader.columns.ExcelColumn;

public abstract class BaseExcelDto {

    @SuppressWarnings("unchecked")
    public void setProperty(ExcelColumn column, String property) {
        column.getSetter().accept(this, property);
    }
}
