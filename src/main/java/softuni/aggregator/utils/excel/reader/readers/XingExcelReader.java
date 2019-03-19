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
    protected Map<String, ReadExcelColumn> getColumns() {
        return Arrays.stream(XingColumn.values())
                .collect(Collectors.toMap(XingColumn::getColumnName, c -> c));
    }

    @Override
    protected XingCompanyDto createInstance() {
        return new XingCompanyDto();
    }
}
