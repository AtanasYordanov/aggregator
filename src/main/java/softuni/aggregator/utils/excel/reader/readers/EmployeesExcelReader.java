package softuni.aggregator.utils.excel.reader.readers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import softuni.aggregator.utils.excel.reader.columns.EmployeeColumn;
import softuni.aggregator.utils.excel.reader.columns.ReadExcelColumn;
import softuni.aggregator.utils.excel.reader.model.EmployeeExcelDto;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Qualifier("employees")
public class EmployeesExcelReader extends BaseExcelReader<EmployeeExcelDto> {

    @Override
    protected Map<String, ReadExcelColumn> getColumns() {
        return Arrays.stream(EmployeeColumn.values())
                .collect(Collectors.toMap(EmployeeColumn::getColumnName, e -> e));
    }

    @Override
    protected EmployeeExcelDto createInstance() {
        return new EmployeeExcelDto();
    }
}
