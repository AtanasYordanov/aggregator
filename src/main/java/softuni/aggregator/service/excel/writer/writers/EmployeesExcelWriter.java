package softuni.aggregator.service.excel.writer.writers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import softuni.aggregator.service.excel.constants.ExcelConstants;
import softuni.aggregator.service.excel.writer.columns.EmployeesExportColumn;
import softuni.aggregator.service.excel.writer.columns.WriteExcelColumn;
import softuni.aggregator.service.excel.writer.model.EmployeesExportExcelDto;

@Component
@Qualifier("employees")
public class EmployeesExcelWriter extends BaseExcelWriter<EmployeesExportExcelDto> {

    @Override
    protected WriteExcelColumn[] getColumns() {
        return EmployeesExportColumn.values();
    }

    @Override
    protected String getExportName() {
        return ExcelConstants.EMPLOYEES_EXPORT_NAME;
    }
}
