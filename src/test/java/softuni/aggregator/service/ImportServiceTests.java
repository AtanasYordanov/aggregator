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
import org.springframework.web.multipart.MultipartFile;
import softuni.aggregator.domain.entities.*;
import softuni.aggregator.domain.model.vo.page.ImportsPageVO;
import softuni.aggregator.domain.repository.ImportRepository;
import softuni.aggregator.service.excel.reader.ExcelReader;
import softuni.aggregator.service.excel.reader.imports.ImportType;
import softuni.aggregator.service.excel.reader.model.EmployeeImportDto;
import softuni.aggregator.service.excel.reader.model.ExcelImportDto;
import softuni.aggregator.service.excel.reader.model.OrbisCompanyImportDto;
import softuni.aggregator.service.excel.reader.model.XingCompanyImportDto;
import softuni.aggregator.service.impl.ImportServiceImpl;

import javax.servlet.ServletContext;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ImportServiceTests {

    @Mock
    private ImportRepository mockImportRepository;

    @Mock
    private CompanyService mockCompanyService;

    @Mock
    private EmployeeService mockEmployeeService;

    @Mock
    private SubIndustryService mockSubIndustryService;

    @Mock
    private MainIndustryService mockMainIndustryService;

    @Mock
    private ServletContext mockServletContext;

    @Mock
    private ExcelReader mockExcelReader;

    private ImportService importService;

    @Before
    public void init() {
        ModelMapper mapper = new ModelMapper();
        importService = new ImportServiceImpl(mockCompanyService, mockEmployeeService, mockSubIndustryService,
                mockMainIndustryService, mockImportRepository, mockServletContext, mockExcelReader, mapper);
    }

    @Test
    public void getImportsPage_always_shouldFillPageVoCorrectly() {
        Long testExportsCount = 50L;
        User user = new User();
        Pageable pageable = PageRequest.of(1, 20);

        List<Import> imports = buildImports();

        Mockito.when(mockImportRepository.findAllByUser(user, pageable)).thenReturn(imports);
        Mockito.when(mockImportRepository.countByUser(user)).thenReturn(testExportsCount);

        ImportsPageVO importsPageVO = importService.getImportsPage(pageable, user);

        Assert.assertEquals(imports.size(), importsPageVO.getImports().size());
        Assert.assertEquals((long) testExportsCount, importsPageVO.getTotalItemsCount());
        for (Import imp : imports) {
            boolean containsExport = importsPageVO.getImports().stream()
                    .anyMatch(i -> i.getNewEntriesCount() == imp.getNewEntriesCount());
            Assert.assertTrue(containsExport);
        }
    }

    @Test
    public void getAllImportsPage_always_shouldFillPageVoCorrectly() {
        Long testExportsCount = 50L;
        Pageable pageable = PageRequest.of(1, 20);

        List<Import> imports = buildImports();
        Page<Import> importPage = new PageImpl<>(imports);

        Mockito.when(mockImportRepository.findAll(pageable)).thenReturn(importPage);
        Mockito.when(mockImportRepository.count()).thenReturn(testExportsCount);

        ImportsPageVO importsPageVO = importService.getAllImportsPage(pageable);

        Assert.assertEquals(imports.size(), importsPageVO.getImports().size());
        Assert.assertEquals((long) testExportsCount, importsPageVO.getTotalItemsCount());
        for (Import imp : imports) {
            boolean containsExport = importsPageVO.getImports().stream()
                    .anyMatch(i -> i.getNewEntriesCount() == imp.getNewEntriesCount());
            Assert.assertTrue(containsExport);
        }
    }

    @Test
    public void getImportTypes_always_shouldReturnCorrectImportTypeKeyValueMap() {
        Map<String, String> importTypesMap = importService.getImportTypes();

        ImportType[] importTypes = ImportType.values();
        Assert.assertEquals(importTypes.length, importTypesMap.size());
        for (ImportType importType : importTypes) {
            Assert.assertEquals(importType.toString(), importTypesMap.get(importType.getEndpoint()));
        }
    }

    @Test
    public void importCompaniesFromXing_always_shouldReadMapAndSaveData() {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        Mockito.when(mockFile.getOriginalFilename()).thenReturn("someName");

        List<ExcelImportDto> data = List.of(
                new XingCompanyImportDto(),
                new XingCompanyImportDto(),
                new XingCompanyImportDto()
        );

        Map<String, MainIndustry> mainIndustryMap = Map.of("name", new MainIndustry());
        Map<String, SubIndustry> subIndustryMap = Map.of("name", new SubIndustry());
        Map<String, Company> companiesMap = Map.of("website", new Company());

        Mockito.when(mockExcelReader.readExcel(Mockito.any(), Mockito.any())).thenReturn(data);
        Mockito.when(mockMainIndustryService.getAllIndustriesByName()).thenReturn(mainIndustryMap);
        Mockito.when(mockSubIndustryService.getAllIndustriesByName()).thenReturn(subIndustryMap);
        Mockito.when(mockCompanyService.getCompaniesByWebsite(Mockito.any())).thenReturn(companiesMap);

        Mockito.verify(mockSubIndustryService).saveAll(subIndustryMap.values());
        Mockito.verify(mockCompanyService).saveCompanies(companiesMap.values());
        Mockito.verify(mockImportRepository).save(Mockito.any());
    }

    @Test
    public void importCompaniesFromOrbis_always_shouldReadMapAndSaveData() {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        Mockito.when(mockFile.getOriginalFilename()).thenReturn("someName");

        List<ExcelImportDto> data = List.of(
                new OrbisCompanyImportDto(),
                new OrbisCompanyImportDto(),
                new OrbisCompanyImportDto()
        );

        Map<String, Company> companiesMap = Map.of("website", new Company());

        Mockito.when(mockExcelReader.readExcel(Mockito.any(), Mockito.any())).thenReturn(data);
        Mockito.when(mockCompanyService.getCompaniesByWebsite(Mockito.any())).thenReturn(companiesMap);

        Mockito.verify(mockCompanyService).saveCompanies(companiesMap.values());
        Mockito.verify(mockImportRepository).save(Mockito.any());
    }

    @Test
    public void importEmployees_always_shouldReadMapAndSaveData() {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        Mockito.when(mockFile.getOriginalFilename()).thenReturn("someName");

        EmployeeImportDto employeeImportDto1 = new EmployeeImportDto();
        employeeImportDto1.setEmail("someEmail1");
        EmployeeImportDto employeeImportDto2 = new EmployeeImportDto();
        employeeImportDto2.setEmail("someEmail2");
        EmployeeImportDto employeeImportDto3 = new EmployeeImportDto();
        employeeImportDto3.setEmail("someEmail3");

        List<ExcelImportDto> data = List.of(
                employeeImportDto1,
                employeeImportDto2,
                employeeImportDto3
        );

        Map<String, Employee> employeesMap = new HashMap<>() {{
            put("email", new Employee());
        }};
        Map<String, Company> companiesMap = Map.of("website", new Company());

        Mockito.when(mockExcelReader.readExcel(Mockito.any(), Mockito.any())).thenReturn(data);
        Mockito.when(mockEmployeeService.getEmployeesByEmail(Mockito.any())).thenReturn(employeesMap);
        Mockito.when(mockCompanyService.getCompaniesByWebsite(Mockito.any())).thenReturn(companiesMap);

        Mockito.verify(mockEmployeeService).saveEmployees(employeesMap.values());
        Mockito.verify(mockImportRepository).save(Mockito.any());
    }

    private List<Import> buildImports() {
        Import import1 = new Import();
        import1.setImportType(ImportType.EMPLOYEES);
        import1.setUser(new User());
        import1.setDate(LocalDateTime.now());
        import1.setNewEntriesCount(2000);
        import1.setTotalItemsCount(4000);


        Import import2 = new Import();
        import2.setImportType(ImportType.ORBIS_COMPANIES);
        import2.setUser(new User());
        import2.setDate(LocalDateTime.now().minusHours(9));
        import2.setNewEntriesCount(1000);
        import2.setTotalItemsCount(1000);

        Import import3 = new Import();
        import3.setImportType(ImportType.XING_COMPANIES);
        import3.setUser(new User());
        import3.setDate(LocalDateTime.now().minusDays(1));
        import3.setNewEntriesCount(500);
        import3.setTotalItemsCount(6000);

        return List.of(import1, import2, import3);
    }
}
