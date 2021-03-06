package softuni.aggregator.service.excel.writer;

import softuni.aggregator.service.excel.writer.exports.ExportType;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;

import java.io.File;
import java.util.List;

public interface ExcelWriter {

    File writeExcel(List<ExcelExportDto> writeExcelDto, ExportType exportType);
}
