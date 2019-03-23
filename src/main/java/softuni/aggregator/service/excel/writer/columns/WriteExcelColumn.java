package softuni.aggregator.service.excel.writer.columns;

import softuni.aggregator.service.excel.common.ExcelColumn;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;

import java.util.function.Function;

public interface WriteExcelColumn<T extends ExcelExportDto> extends ExcelColumn {

    Function<T, ?> getGetter();
}
