package softuni.aggregator.utils.excelreader.columns;

import softuni.aggregator.utils.excelreader.model.BaseExcelDto;

import java.util.function.BiConsumer;

public interface ExcelColumn<T extends BaseExcelDto> {

    BiConsumer<T, String> getSetter();
}
