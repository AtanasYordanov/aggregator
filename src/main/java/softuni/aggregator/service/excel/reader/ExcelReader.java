package softuni.aggregator.service.excel.reader;

import softuni.aggregator.service.excel.reader.model.ImportExcelDto;

import java.util.List;

public interface ExcelReader<T extends ImportExcelDto> {

    List<T> readExcel(String path);
}
