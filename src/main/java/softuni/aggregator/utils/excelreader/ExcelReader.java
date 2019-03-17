package softuni.aggregator.utils.excelreader;

import softuni.aggregator.utils.excelreader.model.BaseExcelDto;

import java.util.List;

public interface ExcelReader<T extends BaseExcelDto> {

    List<T> readExcel(String path);
}
