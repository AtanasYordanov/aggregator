package softuni.aggregator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.aggregator.domain.entities.Export;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.binding.CompaniesFilterDataModel;
import softuni.aggregator.domain.model.vo.ExportListVO;
import softuni.aggregator.domain.repository.ExportRepository;
import softuni.aggregator.service.CompanyService;
import softuni.aggregator.service.EmployeeService;
import softuni.aggregator.service.ExportService;
import softuni.aggregator.service.excel.constants.ExcelConstants;
import softuni.aggregator.service.excel.writer.exports.ExportType;
import softuni.aggregator.service.excel.writer.ExcelWriterImpl;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ExportServiceImpl implements ExportService {

    private final ExportRepository exportRepository;
    private final ExcelWriterImpl excelWriter;
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ModelMapper mapper;

    @Autowired
    public ExportServiceImpl(ExportRepository exportRepository, ExcelWriterImpl excelWriter,
                             EmployeeService employeesService, CompanyService companyService,
                             ModelMapper mapper) {
        this.exportRepository = exportRepository;
        this.employeeService = employeesService;
        this.excelWriter = excelWriter;
        this.companyService = companyService;
        this.mapper = mapper;
    }

    @Override
    public int exportEmployees(User user) {
        List<ExcelExportDto> allEmployees = employeeService.getEmployeesForExport();
        File file = excelWriter.writeExcel(allEmployees, ExportType.EMPLOYEES);
        int itemsCount = allEmployees.size();
        Export export = new Export(file.getName(), ExportType.EMPLOYEES, itemsCount, user);
        exportRepository.save(export);
        return itemsCount;
    }

    @Override
    public int exportCompanies(CompaniesFilterDataModel filterData, User user) {
        List<ExcelExportDto> companies = companyService.getCompaniesForExport(filterData);
        File file = excelWriter.writeExcel(companies, ExportType.COMPANIES);
        int itemsCount = companies.size();
        Export export = new Export(file.getName(), ExportType.COMPANIES, itemsCount, user);
        exportRepository.save(export);
        return itemsCount;
    }

    @Override
    public byte[] getExport(HttpServletResponse response, Long exportId) {
        Export export = exportRepository.findById(exportId).orElseThrow();
        File file = new File(ExcelConstants.EXPORT_BASE_PATH + export.getName());
        return getBytes(response, file);
    }

    @Override
    public List<ExportListVO> getExportsPage(Pageable pageable, User user) {
        return exportRepository.findAllByUser(user, pageable).stream()
                .map(e -> mapper.map(e, ExportListVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public long getExportsCount(User user) {
        return exportRepository.countByUser(user);
    }

    @Override
    public void deleteOldExports() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC).minusMonths(1);

        List<Export> exports = exportRepository.findAllByGeneratedOnBefore(now);

        exports.stream()
                .map(e -> new File(ExcelConstants.EXPORT_BASE_PATH + e.getName()))
                .filter(f -> !f.delete())
                .forEach(f -> log.error("Failed to delete file {}", f.getName()));

        exportRepository.deleteAll(exports);
    }

    private byte[] getBytes(HttpServletResponse response, File file) {
        try {
            InputStream in = new FileInputStream(file);
            response.setContentType("text/xml");
            response.setHeader("Content-Disposition", "filename=" + file.getName());
            byte[] byteResponse = IOUtils.toByteArray(in);
            in.close();
            return byteResponse;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(String.format("Failed to export file: %s", file.getName()));
        }
    }
}
