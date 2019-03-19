package softuni.aggregator.utils.excel.writer.writers;

import org.springframework.stereotype.Component;
import softuni.aggregator.utils.excel.constants.ExcelConstants;
import softuni.aggregator.utils.excel.writer.columns.ExportEmployeesColumn;
import softuni.aggregator.utils.excel.writer.columns.WriteExcelColumn;
import softuni.aggregator.utils.excel.writer.model.WriteEmployeesExcelDto;

@Component
public class EmployeesExcelWriter extends BaseExcelWriter<WriteEmployeesExcelDto> {

    @Override
    protected WriteExcelColumn[] getColumns() {
        return ExportEmployeesColumn.values();
    }

    @Override
    protected String getExportType() {
        return ExcelConstants.EMPLOYEES_EXPORT_NAME;
    }
}
