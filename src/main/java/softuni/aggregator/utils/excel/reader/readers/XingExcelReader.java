package softuni.aggregator.utils.excel.reader.readers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import softuni.aggregator.utils.excel.reader.columns.ReadExcelColumn;
import softuni.aggregator.utils.excel.reader.columns.XingColumn;
import softuni.aggregator.utils.excel.reader.model.XingCompanyDto;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Qualifier("xing")
public class XingExcelReader extends BaseExcelReader<XingCompanyDto> {

    @Override
    public List<XingCompanyDto> readExcel(String path) {
        Map<String, ReadExcelColumn> columns = Arrays.stream(XingColumn.values())
                .collect(Collectors.toMap(XingColumn::getColumnName, c -> c));

        return super.readExcel(path, columns);
    }

    @Override
    protected XingCompanyDto createInstance() {
        return new XingCompanyDto();
    }
}
