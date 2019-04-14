package softuni.aggregator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.aggregator.constants.ErrorMessages;
import softuni.aggregator.domain.entities.Export;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.AsyncTaskType;
import softuni.aggregator.domain.model.binding.ExportBindingModel;
import softuni.aggregator.domain.model.binding.FilterDataModel;
import softuni.aggregator.domain.model.vo.ExportListVO;
import softuni.aggregator.domain.model.vo.page.ExportsPageVO;
import softuni.aggregator.domain.repository.ExportRepository;
import softuni.aggregator.service.CompanyService;
import softuni.aggregator.service.EmployeeService;
import softuni.aggregator.service.ExportService;
import softuni.aggregator.service.AsyncTaskService;
import softuni.aggregator.service.excel.constants.ExcelConstants;
import softuni.aggregator.service.excel.writer.ExcelWriter;
import softuni.aggregator.service.excel.writer.exports.ExportType;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;
import softuni.aggregator.web.exceptions.NotFoundException;
import softuni.aggregator.web.exceptions.ServiceException;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ExportServiceImpl implements ExportService {

    private final ExportRepository exportRepository;
    private final ExcelWriter excelWriter;
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final AsyncTaskService asyncTaskService;
    private final ModelMapper mapper;

    @Autowired
    public ExportServiceImpl(ExportRepository exportRepository, ExcelWriter excelWriter,
                             EmployeeService employeesService, CompanyService companyService,
                             AsyncTaskService asyncTaskService, ModelMapper mapper) {
        this.exportRepository = exportRepository;
        this.employeeService = employeesService;
        this.excelWriter = excelWriter;
        this.companyService = companyService;
        this.asyncTaskService = asyncTaskService;
        this.mapper = mapper;
    }

    @Async
    @Override
    public void exportCompanies(User user, ExportBindingModel exportModel, FilterDataModel filterData) {
        asyncTaskService.registerTask(user.getId(), AsyncTaskType.COMPANIES_EXPORT);
        try {
            List<ExcelExportDto> companies = companyService.getCompaniesForExport(filterData);
            File file = excelWriter.writeExcel(companies, ExportType.COMPANIES);
            int itemsCount = companies.size();
            Export export = new Export(exportModel.getExportName(), file.getName(), ExportType.COMPANIES, itemsCount, user);
            exportRepository.save(export);
            asyncTaskService.markTaskAsFinished(user.getId(), AsyncTaskType.COMPANIES_EXPORT, itemsCount);
        } catch (Throwable t) {
            asyncTaskService.markTaskAsFinished(user.getId(), AsyncTaskType.COMPANIES_EXPORT, 0);
            throw new ServiceException(t.getMessage());

        }
    }

    @Async
    @Override
    public void exportEmployees(User user, ExportBindingModel exportModel, FilterDataModel filterData) {
        asyncTaskService.registerTask(user.getId(), AsyncTaskType.EMPLOYEES_EXPORT);
        try {
            List<ExcelExportDto> allEmployees = employeeService.getEmployeesForExport(filterData);
            File file = excelWriter.writeExcel(allEmployees, ExportType.EMPLOYEES);
            int itemsCount = allEmployees.size();
            Export export = new Export(exportModel.getExportName(), file.getName(), ExportType.EMPLOYEES, itemsCount, user);
            exportRepository.save(export);
            asyncTaskService.markTaskAsFinished(user.getId(), AsyncTaskType.EMPLOYEES_EXPORT, itemsCount);
        } catch (Throwable t) {
            asyncTaskService.markTaskAsFinished(user.getId(), AsyncTaskType.EMPLOYEES_EXPORT, 0);
            throw new ServiceException(t.getMessage());
        }
    }

    @Async
    @Override
    public void exportEmployeesWithCompanies(User user, ExportBindingModel exportModel, FilterDataModel filterData) {
        asyncTaskService.registerTask(user.getId(), AsyncTaskType.EMPLOYEES_EXPORT);
        try {
            List<ExcelExportDto> data = employeeService.getEmployeesWithCompaniesForExport(filterData);
            File file = excelWriter.writeExcel(data, ExportType.MIXED);
            int itemsCount = data.size();
            Export export = new Export(exportModel.getExportName(), file.getName(), ExportType.MIXED, itemsCount, user);
            exportRepository.save(export);
            asyncTaskService.markTaskAsFinished(user.getId(), AsyncTaskType.EMPLOYEES_EXPORT, itemsCount);
        } catch (Throwable t) {
            asyncTaskService.markTaskAsFinished(user.getId(), AsyncTaskType.EMPLOYEES_EXPORT, 0);
            throw new ServiceException(t.getMessage());
        }
    }

    @Override
    public byte[] getExport(HttpServletResponse response, Long exportId) {
        Export export = exportRepository.findById(exportId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.EXPORT_NOT_FOUND));
        File file = new File(ExcelConstants.EXPORT_BASE_PATH + export.getFileName());
        return getBytes(response, file, export.getExportName());
    }

    @Override
    public ExportsPageVO getExportsPage(Pageable pageable, User user) {
        List<ExportListVO> exports = exportRepository.findAllByUser(user, pageable).stream()
                .map(e -> mapper.map(e, ExportListVO.class))
                .collect(Collectors.toList());

        long exportsCount = exportRepository.countByUser(user);

        ExportsPageVO exportsPageVO = new ExportsPageVO();
        exportsPageVO.setExports(exports);
        exportsPageVO.setTotalItemsCount(exportsCount);

        return exportsPageVO;
    }

    @Override
    public ExportsPageVO getAllExportsPage(Pageable pageable) {
        List<ExportListVO> exports = exportRepository.findAll(pageable).stream()
                .map(e -> {
                    ExportListVO exportVO = mapper.map(e, ExportListVO.class);
                    exportVO.setUserEmail(e.getUser().getEmail());
                    return exportVO;
                })
                .collect(Collectors.toList());

        long exportsCount = exportRepository.count();

        ExportsPageVO exportsPageVO = new ExportsPageVO();
        exportsPageVO.setExports(exports);
        exportsPageVO.setTotalItemsCount(exportsCount);

        return exportsPageVO;
    }

    @Override
    public void deleteOldExports() {
        log.info("Deleting old exports...");

        LocalDateTime now = LocalDateTime.now().minusMonths(1);
        List<Export> exports = exportRepository.findAllByGeneratedOnBefore(now);

        exports.stream()
                .map(e -> new File(ExcelConstants.EXPORT_BASE_PATH + e.getFileName()))
                .filter(f -> !f.delete())
                .forEach(f -> log.error("Failed to delete file {}", f.getName()));

        exportRepository.deleteAll(exports);
    }

    private byte[] getBytes(HttpServletResponse response, File file, String exportName) {
        try {
            InputStream in = new FileInputStream(file);
            response.setContentType(MediaType.TEXT_XML_VALUE);
            response.setHeader("Content-Disposition", "filename=" + exportName + ExcelConstants.EXPORT_FILE_EXTENSION);
            byte[] byteResponse = IOUtils.toByteArray(in);
            in.close();
            return byteResponse;
        } catch (IOException e) {
            throw new ServiceException(ErrorMessages.EXPORT_FAILED);
        }
    }
}
