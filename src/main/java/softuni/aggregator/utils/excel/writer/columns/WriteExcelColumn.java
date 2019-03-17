package softuni.aggregator.utils.excel.writer.columns;

import softuni.aggregator.utils.excel.common.ExcelColumn;
import softuni.aggregator.utils.excel.writer.model.WriteExcelDto;

import java.util.function.Function;

public interface WriteExcelColumn<T extends WriteExcelDto> extends ExcelColumn {

    Function<T, ?> getGetter();
}
