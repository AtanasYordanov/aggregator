package softuni.aggregator.service.excel.reader;

import softuni.aggregator.service.excel.reader.imports.Import;
import softuni.aggregator.service.excel.reader.model.ExcelImportDto;

import java.util.List;

public interface ExcelReader {

    <T extends ExcelImportDto> List<T> readExcel(String path, Import excelImport);
}
