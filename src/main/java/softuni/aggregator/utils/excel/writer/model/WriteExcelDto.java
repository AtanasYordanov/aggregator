package softuni.aggregator.utils.excel.writer.model;

import softuni.aggregator.utils.excel.writer.columns.WriteExcelColumn;

public abstract class WriteExcelDto {

    @SuppressWarnings("unchecked")
    public Object getProperty(WriteExcelColumn column) {
        return column.getGetter().apply(this);
    }
}
