package softuni.aggregator.utils.excel.reader.readers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import softuni.aggregator.utils.excel.reader.columns.OrbisColumn;
import softuni.aggregator.utils.excel.reader.columns.ReadExcelColumn;
import softuni.aggregator.utils.excel.reader.model.OrbisCompanyDto;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Qualifier("orbis")
public class OrbisExcelReader extends BaseExcelReader<OrbisCompanyDto> {

    @Override
    public List<OrbisCompanyDto> readExcel(String path) {
        Map<String, ReadExcelColumn> columns = Arrays.stream(OrbisColumn.values())
                .collect(Collectors.toMap(OrbisColumn::getColumnName, c -> c));

        return super.readExcel(path, columns);
    }

    @Override
    protected OrbisCompanyDto createInstance() {
        return new OrbisCompanyDto();
    }
}
