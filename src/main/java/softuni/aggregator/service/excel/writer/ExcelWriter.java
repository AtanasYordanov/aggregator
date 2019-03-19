package softuni.aggregator.service.excel.writer;

import softuni.aggregator.service.excel.writer.model.ExportExcelDto;

import java.io.File;
import java.util.List;

public interface ExcelWriter<T extends ExportExcelDto> {

    File writeExcel(List<T> writeExcelDto);
}
