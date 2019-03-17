package softuni.aggregator.service.excel;

import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.service.api.EmployeeService;
import softuni.aggregator.utils.excel.writer.model.WriteEmployeesExcelDto;
import softuni.aggregator.utils.excel.writer.writers.EmployeesExcelWriter;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
@Transactional
public class ExportExcelServiceImpl implements ExportExcelService {

    private final EmployeesExcelWriter employeesExcelWriter;
    private final EmployeeService employeesService;
    private final ModelMapper modelMapper;

    @Autowired
    public ExportExcelServiceImpl(EmployeesExcelWriter employeesExcelWriter, EmployeeService employeesService, ModelMapper modelMapper) {
        this.employeesExcelWriter = employeesExcelWriter;
        this.employeesService = employeesService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void exportEmployees() {
        List<WriteEmployeesExcelDto> allEmployees = employeesService.getAllEmployees().stream()
                .map(e -> modelMapper.map(e, WriteEmployeesExcelDto.class))
                .collect(Collectors.toList());

        employeesExcelWriter.writeExcel(allEmployees);
        log.info(String.format("Successfully exported %s", allEmployees.size()));
    }
}
