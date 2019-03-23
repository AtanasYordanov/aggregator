package softuni.aggregator.service.excel;

import lombok.extern.java.Log;
import org.apache.commons.compress.utils.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import softuni.aggregator.service.api.CompanyService;
import softuni.aggregator.service.api.EmployeeService;
import softuni.aggregator.service.excel.writer.model.CompaniesExportExcelDto;
import softuni.aggregator.service.excel.writer.model.EmployeesExportExcelDto;
import softuni.aggregator.service.excel.writer.writers.CompaniesExcelWriter;
import softuni.aggregator.service.excel.writer.writers.EmployeesExcelWriter;
import softuni.aggregator.utils.performance.PerformanceUtils;

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
    private final CompaniesExcelWriter companiesExcelWriter;
    private final EmployeeService employeesService;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @Autowired
    public ExportExcelServiceImpl(@Qualifier("employees") EmployeesExcelWriter employeesExcelWriter,
                                  @Qualifier("companies") CompaniesExcelWriter companiesExcelWriter,
                                  EmployeeService employeesService, CompanyService companyService, ModelMapper modelMapper) {
        this.employeesExcelWriter = employeesExcelWriter;
        this.companiesExcelWriter = companiesExcelWriter;
        this.employeesService = employeesService;
        this.companyService = companyService;
        this.modelMapper = modelMapper;
    }

    @Override
    public byte[] exportEmployees(HttpServletResponse response) {
        PerformanceUtils.startTimer("Export Employees");

        List<EmployeesExportExcelDto> allEmployees = employeesService.getEmployeesForExport().stream()
                .map(e -> modelMapper.map(e, EmployeesExportExcelDto.class))
                .collect(Collectors.toList());

        File file = employeesExcelWriter.writeExcel(allEmployees);

        PerformanceUtils.stopTimer("Export Employees");

        return getBytes(response, file);
    }

    @Override
    public byte[] exportCompanies(HttpServletResponse response) {
        PerformanceUtils.startTimer("Export Companies");

        List<CompaniesExportExcelDto> allCompanies = companyService.getCompaniesForExport();
        File file = companiesExcelWriter.writeExcel(allCompanies);

        PerformanceUtils.stopTimer("Export Companies");
        return getBytes(response, file);
    }

    private byte[] getBytes(HttpServletResponse response, File file) {
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
