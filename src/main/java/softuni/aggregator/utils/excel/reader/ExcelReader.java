package softuni.aggregator.utils.excel.reader;

import softuni.aggregator.utils.excel.reader.model.ReadExcelDto;

import java.util.List;

public interface ExcelReader<T extends ReadExcelDto> {

    List<T> readExcel(String path);
}
