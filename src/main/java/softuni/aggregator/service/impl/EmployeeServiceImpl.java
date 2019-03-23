package softuni.aggregator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.repository.EmployeeRepository;
import softuni.aggregator.service.EmployeeService;
import softuni.aggregator.service.excel.writer.model.EmployeesExportDto;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<ExcelExportDto> getEmployeesForExport() {
        return employeeRepository.findAll().stream()
                .map(EmployeesExportDto::new)
                .collect(Collectors.toList());
    }
}
