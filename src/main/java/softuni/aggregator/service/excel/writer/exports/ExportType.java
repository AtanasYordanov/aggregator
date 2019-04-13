package softuni.aggregator.service.excel.writer.exports;

import lombok.AllArgsConstructor;
import softuni.aggregator.service.excel.writer.columns.CompaniesExportColumn;
import softuni.aggregator.service.excel.writer.columns.EmployeesCompaniesExportColumn;
import softuni.aggregator.service.excel.writer.columns.EmployeesExportColumn;
import softuni.aggregator.service.excel.writer.columns.WriteExcelColumn;

@AllArgsConstructor
public enum ExportType {

    EMPLOYEES(EmployeesExportColumn.values()),
    COMPANIES(CompaniesExportColumn.values()),
    MIXED(EmployeesCompaniesExportColumn.values());

    private WriteExcelColumn[] columns;

    public WriteExcelColumn[] getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
