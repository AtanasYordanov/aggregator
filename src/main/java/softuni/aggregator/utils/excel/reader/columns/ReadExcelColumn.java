package softuni.aggregator.utils.excel.reader.columns;

import softuni.aggregator.utils.excel.reader.model.ReadExcelDto;

import java.util.function.BiConsumer;

public interface ReadExcelColumn<T extends ReadExcelDto> {

    BiConsumer<T, String> getSetter();
}
