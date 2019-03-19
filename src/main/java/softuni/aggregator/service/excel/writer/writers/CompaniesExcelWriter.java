package softuni.aggregator.service.excel.writer.writers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import softuni.aggregator.service.excel.constants.ExcelConstants;
import softuni.aggregator.service.excel.writer.columns.CompaniesExportColumn;
import softuni.aggregator.service.excel.writer.columns.WriteExcelColumn;
import softuni.aggregator.service.excel.writer.model.CompaniesExportExcelDto;

@Component
@Qualifier("companies")
public class CompaniesExcelWriter extends BaseExcelWriter<CompaniesExportExcelDto> {

    @Override
    protected WriteExcelColumn[] getColumns() {
        return CompaniesExportColumn.values();
    }

    @Override
    protected String getExportType() {
        return ExcelConstants.COMPANIES_EXPORT_NAME;
    }
}
