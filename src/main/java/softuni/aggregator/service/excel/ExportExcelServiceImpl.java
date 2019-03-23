package softuni.aggregator.service.excel;

import lombok.extern.java.Log;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.service.CompanyService;
import softuni.aggregator.service.EmployeeService;
import softuni.aggregator.service.excel.writer.exports.Export;
import softuni.aggregator.service.excel.writer.ExcelWriterImpl;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;
import softuni.aggregator.utils.performance.PerformanceUtils;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.util.List;

@Log
@Service
@Transactional
public class ExportExcelServiceImpl implements ExportExcelService {

    private final ExcelWriterImpl excelWriter;
    private final CompanyService companyService;
    private final EmployeeService employeeService;

    @Autowired
    public ExportExcelServiceImpl(ExcelWriterImpl excelWriter, EmployeeService employeesService,
                                  CompanyService companyService) {
        this.employeeService = employeesService;
        this.excelWriter = excelWriter;
        this.companyService = companyService;
    }

    @Override
    public byte[] exportEmployees(HttpServletResponse response) {
        PerformanceUtils.startTimer("Export Employees");

        List<ExcelExportDto> allEmployees = employeeService.getEmployeesForExport();

        File file = excelWriter.writeExcel(allEmployees, Export.EMPLOYEES);

        PerformanceUtils.stopTimer("Export Employees");

        return getBytes(response, file);
    }

    @Override
    public byte[] exportCompanies(HttpServletResponse response) {
        PerformanceUtils.startTimer("Export Companies");

        List<ExcelExportDto> allCompanies = companyService.getCompaniesForExport();

        File file = excelWriter.writeExcel(allCompanies, Export.COMPANIES);

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
