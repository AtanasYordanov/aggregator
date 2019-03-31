package softuni.aggregator.service.excel.writer.exports;

import softuni.aggregator.service.excel.writer.columns.CompaniesExportColumn;
import softuni.aggregator.service.excel.writer.columns.EmployeesExportColumn;
import softuni.aggregator.service.excel.writer.columns.WriteExcelColumn;

public enum ExportType {
    EMPLOYEES(EmployeesExportColumn.values()),
    COMPANIES(CompaniesExportColumn.values());

    private WriteExcelColumn[] columns;

    ExportType(WriteExcelColumn[] columns) {
        this.columns = columns;
    }

    public WriteExcelColumn[] getColumns() {
        return columns;
    }

    public String getExportName() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}