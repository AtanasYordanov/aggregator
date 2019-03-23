package softuni.aggregator.service;

import softuni.aggregator.service.excel.writer.model.ExcelExportDto;

import java.util.List;

public interface EmployeeService {

    List<ExcelExportDto> getEmployeesForExport();
}
