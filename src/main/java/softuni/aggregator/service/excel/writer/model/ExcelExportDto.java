package softuni.aggregator.service.excel.writer.model;

import softuni.aggregator.service.excel.writer.columns.WriteExcelColumn;

public abstract class ExcelExportDto {

    @SuppressWarnings("unchecked")
    public Object getProperty(WriteExcelColumn column) {
        return column.getGetter().apply(this);
    }
}
