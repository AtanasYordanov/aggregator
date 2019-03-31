package softuni.aggregator.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.model.vo.EmployeeListVO;
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
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
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
                .map(e ->  modelMapper.map(e, EmployeeListVO.class))
                .collect(Collectors.toList());
    }
}
