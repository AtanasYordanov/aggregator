package softuni.aggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Employee;
import softuni.aggregator.domain.repository.EmployeeRepository;
import softuni.aggregator.service.api.EmployeeService;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getEmployeesForExport() {
        return employeeRepository.findAll();
    }
}
