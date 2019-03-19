package softuni.aggregator.service.excel.reader.readers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import softuni.aggregator.service.excel.reader.columns.EmployeesImportColumn;
import softuni.aggregator.service.excel.reader.model.EmployeeExcelDto;

@Component
@Qualifier("employees")
public class EmployeesExcelReader extends BaseExcelReader<EmployeeExcelDto> {

    @Override
    protected EmployeesImportColumn[] getColumns() {
        return EmployeesImportColumn.values();
    }

    @Override
    protected EmployeeExcelDto createInstance() {
        return new EmployeeExcelDto();
    }
}
