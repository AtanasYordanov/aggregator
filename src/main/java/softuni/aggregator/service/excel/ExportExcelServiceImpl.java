package softuni.aggregator.service.excel;

import lombok.extern.java.Log;
import org.apache.commons.compress.utils.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.service.api.EmployeeService;
import softuni.aggregator.utils.excel.writer.model.WriteEmployeesExcelDto;
import softuni.aggregator.utils.excel.writer.writers.EmployeesExcelWriter;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
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
    public byte[] exportEmployees(HttpServletResponse response) {
        List<WriteEmployeesExcelDto> allEmployees = employeesService.getAllEmployees().stream()
                .map(e -> modelMapper.map(e, WriteEmployeesExcelDto.class))
                .collect(Collectors.toList());

        File file = employeesExcelWriter.writeExcel(allEmployees);

        try {
            InputStream in = new FileInputStream(file);
            response.setContentType("text/xml");
            response.setHeader("Content-Disposition", "filename=" + file.getName());
            byte[] byteResponse = IOUtils.toByteArray(in);
            in.close();
            if (!file.delete()) {
                log.warning(String.format("Failed to delete file: %s", file.getName()));
            }
            return byteResponse;
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Failed to export file: %s", file.getName()));
        }
    }
}
