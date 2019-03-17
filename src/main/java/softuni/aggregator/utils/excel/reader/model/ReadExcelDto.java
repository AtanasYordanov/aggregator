package softuni.aggregator.utils.excel.reader.model;

import softuni.aggregator.utils.excel.reader.columns.ReadExcelColumn;

public abstract class ReadExcelDto {

    @SuppressWarnings("unchecked")
    public void setProperty(ReadExcelColumn column, String property) {
        column.getSetter().accept(this, property);
    }
}
