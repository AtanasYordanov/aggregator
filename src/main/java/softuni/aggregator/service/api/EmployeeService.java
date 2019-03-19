package softuni.aggregator.service.api;

import softuni.aggregator.domain.entities.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getEmployeesForExport();
}
