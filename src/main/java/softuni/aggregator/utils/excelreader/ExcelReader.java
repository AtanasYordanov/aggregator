package softuni.aggregator.utils.excelreader;

import org.springframework.stereotype.Component;
import softuni.aggregator.utils.excelreader.model.BaseExcelDto;

import java.util.List;

@Component
public interface ExcelReader<T extends BaseExcelDto> {

    List<T> readExcel(String path);
}
