package softuni.aggregator.utils.excel.writer;

import softuni.aggregator.utils.excel.writer.model.WriteExcelDto;

import java.io.File;
import java.util.List;

public interface ExcelWriter<T extends WriteExcelDto> {

    File writeExcel(List<T> writeExcelDto);
}
