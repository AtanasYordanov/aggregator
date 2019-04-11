package softuni.aggregator.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Employee;
import softuni.aggregator.domain.model.vo.EmployeeDetailsVO;
import softuni.aggregator.domain.model.vo.EmployeeListVO;
import softuni.aggregator.domain.repository.EmployeeRepository;
import softuni.aggregator.service.EmployeeService;
import softuni.aggregator.service.excel.writer.model.EmployeesExportDto;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;
import org.springframework.transaction.annotation.Transactional;
import softuni.aggregator.web.exceptions.NotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ExcelExportDto> getEmployeesForExport() {
        return employeeRepository.findAll().stream()
                .map(EmployeesExportDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalEmployeesCount() {
        return employeeRepository.count();
    }

    @Override
    public List<EmployeeListVO> getEmployeesPage(Pageable pageable) {
        return employeeRepository.findAll(pageable).stream()
                .map(e ->  mapper.map(e, EmployeeListVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDetailsVO getById(Long id) {
        return employeeRepository.findByIdEager(id)
                .map(c -> mapper.map(c, EmployeeDetailsVO.class))
                .orElseThrow(() -> new NotFoundException("No such employee."));
    }

    @Override
    public void saveEmployees(Collection<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    @Override
    public Map<String, Employee> getEmployeesByEmail(List<String> emails) {
        return employeeRepository.findByEmailIn(emails).stream()
                .collect(Collectors.toMap(Employee::getEmail, e -> e));
    }
}
