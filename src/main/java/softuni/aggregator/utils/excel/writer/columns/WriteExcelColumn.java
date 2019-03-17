package softuni.aggregator.utils.excel.writer.columns;

import softuni.aggregator.utils.excel.writer.model.WriteExcelDto;

import java.util.function.Function;

public interface WriteExcelColumn<T extends WriteExcelDto> {

    Function<T, ?> getGetter();
}
