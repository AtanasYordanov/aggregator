package softuni.aggregator.service.excel.reader.columns;

import softuni.aggregator.service.excel.common.ExcelColumn;
import softuni.aggregator.service.excel.reader.model.ImportExcelDto;

import java.util.function.BiConsumer;

public interface ReadExcelColumn<T extends ImportExcelDto> extends ExcelColumn {

    BiConsumer<T, String> getSetter();
}
