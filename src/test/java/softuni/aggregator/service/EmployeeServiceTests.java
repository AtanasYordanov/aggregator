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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import softuni.aggregator.domain.entities.Employee;
import softuni.aggregator.domain.entities.SubIndustry;
import softuni.aggregator.domain.model.binding.FilterDataModel;
import softuni.aggregator.domain.model.vo.EmployeeDetailsVO;
import softuni.aggregator.domain.model.vo.page.EmployeesPageVO;
import softuni.aggregator.domain.repository.EmployeeRepository;
import softuni.aggregator.service.impl.EmployeeServiceImpl;
import softuni.aggregator.web.exceptions.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository mockEmployeeRepository;

    @Mock
    private SubIndustryService mockSubIndustryService;

    private EmployeeService employeeService;

    @Before
    public void init() {
        ModelMapper mapper = new ModelMapper();
        employeeService = new EmployeeServiceImpl(mockEmployeeRepository, mockSubIndustryService, mapper);
    }

    @Test
    public void getEmployeesForExport_parametersArePresent_shouldCallRepositoryMethodWithCorrectParameters() {
        FilterDataModel filterData = buildFilterData();

        List<SubIndustry> industries = List.of(new SubIndustry());

        Mockito.when(mockSubIndustryService.getIndustries(filterData.getIndustry())).thenReturn(industries);

        employeeService.getEmployeesForExport(filterData);

        Mockito.verify(mockEmployeeRepository).getFilteredEmployees(industries, filterData.getMinEmployeesCount(),
                filterData.getMaxEmployeesCount(), filterData.getIncludeCompaniesWithNoEmployeeData(),
                filterData.getYearFound(), filterData.getCountry(), filterData.getCity());
    }

    @Test
    public void getEmployeesForExport_missingParameters_shouldCallRepositoryMethodWithDefaultParameters() {
        FilterDataModel filterData = new FilterDataModel();
        List<SubIndustry> industries = List.of(new SubIndustry());
        Mockito.when(mockSubIndustryService.getIndustries(filterData.getIndustry())).thenReturn(industries);

        employeeService.getEmployeesForExport(filterData);

        Mockito.verify(mockEmployeeRepository).getFilteredEmployees(industries, 0,
                Integer.MAX_VALUE, null, null, null, null);
    }

    @Test
    public void getEmployeesWithCompaniesForExport_parametersArePresent_shouldCallRepositoryMethodWithCorrectParameters() {
        FilterDataModel filterData = buildFilterData();

        List<SubIndustry> industries = List.of(new SubIndustry());

        Mockito.when(mockSubIndustryService.getIndustries(filterData.getIndustry())).thenReturn(industries);

        employeeService.getEmployeesWithCompaniesForExport(filterData);

        Mockito.verify(mockEmployeeRepository).getFilteredEmployees(industries, filterData.getMinEmployeesCount(),
                filterData.getMaxEmployeesCount(), filterData.getIncludeCompaniesWithNoEmployeeData(),
                filterData.getYearFound(), filterData.getCountry(), filterData.getCity());
    }

    @Test
    public void getEmployeesWithCompaniesForExport_missingParameters_shouldCallRepositoryMethodWithDefaultParameters() {
        FilterDataModel filterData = new FilterDataModel();
        List<SubIndustry> industries = List.of(new SubIndustry());
        Mockito.when(mockSubIndustryService.getIndustries(filterData.getIndustry())).thenReturn(industries);

        employeeService.getEmployeesWithCompaniesForExport(filterData);

        Mockito.verify(mockEmployeeRepository).getFilteredEmployees(industries, 0,
                Integer.MAX_VALUE, null, null, null, null);
    }

    @Test
    public void getCompaniesPage_parametersArePresent_shouldCallRepositoryMethodWithCorrectParameters() {
        FilterDataModel filterData = buildFilterData();
        Pageable pageable = PageRequest.of(1, 20);
        List<SubIndustry> industries = List.of(new SubIndustry());

        Mockito.when(mockSubIndustryService.getIndustries(filterData.getIndustry())).thenReturn(industries);
        Mockito.when(mockEmployeeRepository.getFilteredEmployeesPage(Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(List.of(new Employee()));

        employeeService.getEmployeesPage(pageable, filterData);

        Mockito.verify(mockEmployeeRepository).getFilteredEmployeesPage(pageable, industries, filterData.getMinEmployeesCount(),
                filterData.getMaxEmployeesCount(), filterData.getIncludeCompaniesWithNoEmployeeData(),
                filterData.getYearFound(), filterData.getCountry(), filterData.getCity());
    }

    @Test
    public void getEmployeesPage_missingParameters_shouldCallRepositoryMethodWithDefaultParameters() {
        FilterDataModel filterData = new FilterDataModel();
        Pageable pageable = PageRequest.of(1, 20);
        List<SubIndustry> industries = List.of(new SubIndustry());

        Mockito.when(mockSubIndustryService.getIndustries(filterData.getIndustry())).thenReturn(industries);
        Mockito.when(mockEmployeeRepository.getFilteredEmployeesPage(Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(List.of(new Employee()));

        employeeService.getEmployeesPage(pageable, filterData);

        Mockito.verify(mockEmployeeRepository).getFilteredEmployeesPage(pageable, industries, 0,
                Integer.MAX_VALUE, null, null, null, null);
    }

    @Test
    public void getEmployeesPage_always_shouldFillPageVoCorrectly() {
        Long testCompaniesCount = 50L;
        FilterDataModel filterData = new FilterDataModel();
        Pageable pageable = PageRequest.of(1, 20);
        List<SubIndustry> industries = List.of(new SubIndustry());
        Employee testEmployee = new Employee();
        testEmployee.setEmail("test@test.bg");
        List<Employee> employees = List.of(testEmployee);

        Mockito.when(mockSubIndustryService.getIndustries(filterData.getIndustry())).thenReturn(industries);
        Mockito.when(mockEmployeeRepository.getFilteredEmployeesCount(Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(testCompaniesCount);
        Mockito.when(mockEmployeeRepository.getFilteredEmployeesPage(Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(employees);

        EmployeesPageVO employeesPage = employeeService.getEmployeesPage(pageable, filterData);


        Assert.assertEquals((long) testCompaniesCount, employeesPage.getTotalItemsCount());
        for (Employee employee : employees) {
            boolean containsEmployee = employeesPage.getEmployees().stream()
                    .anyMatch(e -> e.getEmail().equals(employee.getEmail()));
            Assert.assertTrue(containsEmployee);
        }
    }

    @Test
    public void getById_validId_shouldReturnCorrectEmployeeView() {
        Long testId = 1L;

        Employee employee = new Employee();
        employee.setFullName("Test Testov");
        employee.setEmail("test@test.bg");
        employee.setPosition("CEO");
        employee.setHunterIoScore(80);

        Mockito.when(mockEmployeeRepository.findByIdEager(testId)).thenReturn(Optional.of(employee));

        EmployeeDetailsVO employeeDetailsVO = employeeService.getById(testId);

        Assert.assertEquals(employee.getFullName(), employeeDetailsVO.getFullName());
        Assert.assertEquals(employee.getEmail(), employeeDetailsVO.getEmail());
        Assert.assertEquals(employee.getPosition(), employeeDetailsVO.getPosition());
        Assert.assertEquals(employee.getHunterIoScore(), employeeDetailsVO.getHunterIoScore());
    }

    @Test(expected = NotFoundException.class)
    public void getById_nonexistentEmployee_shouldThrowNotFoundException() {
        Long testId = 1L;

        employeeService.getById(testId);
    }

    @Test
    public void saveEmployees_always_shouldCallCorrectRepositoryMethod() {
        List<Employee> employees= List.of(new Employee());
        employeeService.saveEmployees(employees);

        Mockito.verify(mockEmployeeRepository).saveAll(employees);
    }

    @Test
    public void getEmployeesByEmail_always_shouldReturnCorrectEmployeeKeyValueMap() {
        String testEmail1 = "test1@email.bg";
        String testEmail2 = "test2@email.bg";
        String testEmail3 = "test3@email.bg";

        List<String> emails = List.of(testEmail1, testEmail2, testEmail3);

        Employee employee1 = new Employee();
        employee1.setEmail(testEmail1);
        Employee employee2 = new Employee();
        employee2.setEmail(testEmail2);
        Employee employee3 = new Employee();
        employee3.setEmail(testEmail3);

        List<Employee> employees = List.of(employee1, employee2, employee3);

        Mockito.when(mockEmployeeRepository.findByEmailIn(emails)).thenReturn(employees);

        Map<String, Employee> employeesByEmail = employeeService.getEmployeesByEmail(emails);

        Assert.assertEquals(employees.size(), employeesByEmail.size());
        for (Employee employee : employees) {
            Assert.assertEquals(employee, employeesByEmail.get(employee.getEmail()));
        }
    }

    @Test
    public void deleteEmployee_validId_shouldCallCorrectRepositoryMethod() {
        Long testId = 1L;

        employeeService.deleteEmployee(testId);

        Mockito.verify(mockEmployeeRepository).deleteById(testId);
    }

    private FilterDataModel buildFilterData() {
        FilterDataModel filterData = new FilterDataModel();
        filterData.setIndustry("someIndustry");
        filterData.setMinEmployeesCount(1);
        filterData.setMaxEmployeesCount(100);
        filterData.setYearFound(2010);
        filterData.setCity("Sofia");
        filterData.setCountry("Bulgaria");
        filterData.setIncludeCompaniesWithNoEmployeeData(true);
        return filterData;
    }
}
