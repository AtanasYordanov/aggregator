package softuni.aggregator.utils.excel.reader.columns;

import softuni.aggregator.utils.excel.common.ExcelColumn;
import softuni.aggregator.utils.excel.reader.model.ReadExcelDto;

import java.util.function.BiConsumer;

public interface ReadExcelColumn<T extends ReadExcelDto> extends ExcelColumn {

    BiConsumer<T, String> getSetter();
}
