package softuni.aggregator.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import softuni.aggregator.domain.entities.Export;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.binding.ExportBindingModel;
import softuni.aggregator.domain.model.binding.FilterDataModel;
import softuni.aggregator.domain.model.vo.page.ExportsPageVO;
import softuni.aggregator.domain.repository.ExportRepository;
import softuni.aggregator.service.excel.writer.ExcelWriter;
import softuni.aggregator.service.excel.writer.exports.ExportType;
import softuni.aggregator.service.excel.writer.model.CompanyExportDto;
import softuni.aggregator.service.excel.writer.model.EmployeeExportDto;
import softuni.aggregator.service.excel.writer.model.EmployeeWithCompanyExportDto;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;
import softuni.aggregator.service.impl.ExportServiceImpl;
import softuni.aggregator.web.exceptions.NotFoundException;
import softuni.aggregator.web.exceptions.ServiceException;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ExportServiceTests {

    @Mock
    private ExportRepository mockExportRepository;

    @Mock
    private ExcelWriter mockExcelWriter;

    @Mock
    private EmployeeService mockEmployeeService;

    @Mock
    private CompanyService mockCompanyService;

    @Mock
    private NewsService mockNewsService;

    private ExportService exportService;

    @Before
    public void init() {
        ModelMapper mapper = new ModelMapper();
        exportService = new ExportServiceImpl(mockExportRepository, mockExcelWriter,
                mockEmployeeService, mockCompanyService, mockNewsService, mapper);
    }

    @Test
    public void exportEmployees_always_shouldSaveNewExportWithCorrectData() {
        FilterDataModel filterData = new FilterDataModel();
        ExportBindingModel exportModel = new ExportBindingModel();
        exportModel.setExportName("testName");
        exportModel.setIncludeCompanies(true);

        User user = new User();
        List<ExcelExportDto> employees = List.of(new EmployeeExportDto());
        Mockito.when(mockEmployeeService.getEmployeesForExport(filterData))
                .thenReturn(employees);
        File testFile = new File("someName");
        Mockito.when(mockExcelWriter.writeExcel(employees, ExportType.EMPLOYEES))
                .thenReturn(testFile);

        Export testExport = new Export(exportModel.getExportName(), testFile.getName(), ExportType.EMPLOYEES, employees.size(), user);

        exportService.exportEmployees(user, exportModel, filterData);

        Mockito.verify(mockExportRepository).save(testExport);
    }

    @Test
    public void exportCompanies_always_shouldSaveNewExportWithCorrectData() {
        FilterDataModel filterData = new FilterDataModel();
        ExportBindingModel exportModel = new ExportBindingModel();
        exportModel.setExportName("testName");
        exportModel.setIncludeCompanies(true);

        User user = new User();
        List<ExcelExportDto> companies = List.of(new CompanyExportDto());
        Mockito.when(mockCompanyService.getCompaniesForExport(filterData))
                .thenReturn(companies);
        File testFile = new File("someName");
        Mockito.when(mockExcelWriter.writeExcel(companies, ExportType.COMPANIES))
                .thenReturn(testFile);

        Export testExport = new Export(exportModel.getExportName(), testFile.getName(), ExportType.COMPANIES, companies.size(), user);

        exportService.exportCompanies(user, exportModel, filterData);

        Mockito.verify(mockExportRepository).save(testExport);
    }

    @Test
    public void exportEmployeesWithCompanies_always_shouldSaveNewExportWithCorrectData() {
        FilterDataModel filterData = new FilterDataModel();
        ExportBindingModel exportModel = new ExportBindingModel();
        exportModel.setExportName("testName");
        exportModel.setIncludeCompanies(true);

        User user = new User();
        List<ExcelExportDto> employees = List.of(new EmployeeWithCompanyExportDto());
        Mockito.when(mockEmployeeService.getEmployeesWithCompaniesForExport(filterData))
                .thenReturn(employees);
        File testFile = new File("someName");
        Mockito.when(mockExcelWriter.writeExcel(employees, ExportType.MIXED))
                .thenReturn(testFile);

        Export testExport = new Export(exportModel.getExportName(), testFile.getName(), ExportType.MIXED, employees.size(), user);

        exportService.exportEmployeesWithCompanies(user, exportModel, filterData);

        Mockito.verify(mockExportRepository).save(testExport);
    }

    @Test(expected = ServiceException.class)
    public void getExport_nonexistentFile_shouldThrowServiceException() {
        Mockito.when(mockExportRepository.findById(Mockito.any())).thenReturn(Optional.of(new Export()));
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        exportService.getExport(response, null);
    }

    @Test(expected = NotFoundException.class)
    public void getExport_nonexistentExport_shouldThrowNotFoundException() {
        Mockito.when(mockExportRepository.findById(Mockito.any()))
                .thenReturn(Optional.empty());
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        exportService.getExport(response, null);
    }

    @Test
    public void getExportsPage_always_shouldFillPageVoCorrectly() {
        Long testExportsCount = 50L;
        User user = new User();
        Pageable pageable = PageRequest.of(1, 20);

        List<Export> exports = buildExports();

        Mockito.when(mockExportRepository.findAllByUser(user, pageable)).thenReturn(exports);
        Mockito.when(mockExportRepository.countByUser(user)).thenReturn(testExportsCount);

        ExportsPageVO exportsPageVO = exportService.getExportsPage(pageable, user);

        Assert.assertEquals(exports.size(), exportsPageVO.getExports().size());
        Assert.assertEquals((long) testExportsCount, exportsPageVO.getTotalItemsCount());
        for (Export export : exports) {
            boolean containsExport = exportsPageVO.getExports().stream()
                    .anyMatch(e -> e.getExportName().equals(export.getExportName()));
            Assert.assertTrue(containsExport);
        }
    }

    @Test
    public void getAllExportsPage_always_shouldFillPageVoCorrectly() {
        Long testExportsCount = 50L;
        Pageable pageable = PageRequest.of(1, 20);

        List<Export> exports = buildExports();
        Page<Export> exportsPage = new PageImpl<>(exports);

        Mockito.when(mockExportRepository.findAll(pageable)).thenReturn(exportsPage);
        Mockito.when(mockExportRepository.count()).thenReturn(testExportsCount);

        ExportsPageVO exportsPageVO = exportService.getAllExportsPage(pageable);

        Assert.assertEquals(exports.size(), exportsPageVO.getExports().size());
        Assert.assertEquals((long) testExportsCount, exportsPageVO.getTotalItemsCount());
        for (Export export : exports) {
            boolean containsExport = exportsPageVO.getExports().stream()
                    .anyMatch(e -> e.getExportName().equals(export.getExportName()));
            Assert.assertTrue(containsExport);
        }
    }

    @Test
    public void deleteOldExports_always_shouldFilterAndDeleteExportsOlderThanOneMonth() {
        List<Export> exports = buildExports();
        Mockito.when(mockExportRepository.findAllByGeneratedOnBefore(Mockito.any())).thenReturn(exports);

        exportService.deleteOldExports();

        Mockito.verify(mockExportRepository).deleteAll(exports);
    }

    private List<Export> buildExports() {
        Export export1 = new Export();
        export1.setExportName("name1");
        export1.setUser(new User());
        Export export2 = new Export();
        export2.setExportName("name2");
        export2.setUser(new User());

        return List.of(export1, export2);
    }
}
