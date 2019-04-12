package softuni.aggregator.service;

import org.springframework.data.domain.Pageable;
import softuni.aggregator.domain.entities.Employee;
import softuni.aggregator.domain.model.binding.EmployeesFilterDataModel;
import softuni.aggregator.domain.model.vo.EmployeeDetailsVO;
import softuni.aggregator.domain.model.vo.EmployeeListVO;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface EmployeeService {

    List<ExcelExportDto> getEmployeesForExport(EmployeesFilterDataModel filterData);

    List<ExcelExportDto> getEmployeesWithCompaniesForExport(EmployeesFilterDataModel filterData);

    List<EmployeeListVO> getEmployeesPage(Pageable pageable, EmployeesFilterDataModel filterData);

    long getFilteredEmployeesCount(EmployeesFilterDataModel filterData);

    EmployeeDetailsVO getById(Long id);

    void saveEmployees(Collection<Employee> employees);

    Map<String, Employee> getEmployeesByEmail(List<String> emails);

    void deleteEmployee(Long id);
}
