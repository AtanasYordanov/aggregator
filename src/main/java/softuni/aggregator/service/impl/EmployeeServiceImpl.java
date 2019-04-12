package softuni.aggregator.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Employee;
import softuni.aggregator.domain.entities.SubIndustry;
import softuni.aggregator.domain.model.binding.FilterDataModel;
import softuni.aggregator.domain.model.vo.EmployeeDetailsVO;
import softuni.aggregator.domain.model.vo.EmployeeListVO;
import softuni.aggregator.domain.model.vo.page.EmployeesPageVO;
import softuni.aggregator.domain.repository.EmployeeRepository;
import softuni.aggregator.service.EmployeeService;
import softuni.aggregator.service.SubIndustryService;
import softuni.aggregator.service.excel.writer.model.CompanyExportDto;
import softuni.aggregator.service.excel.writer.model.EmployeeExportDto;
import softuni.aggregator.service.excel.writer.model.EmployeeWithCompanyExportDto;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;
import org.springframework.transaction.annotation.Transactional;
import softuni.aggregator.web.exceptions.NotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final SubIndustryService subIndustryService;
    private final ModelMapper mapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, SubIndustryService subIndustryService, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.subIndustryService = subIndustryService;
        this.mapper = mapper;
    }

    @Override
    @SuppressWarnings("Duplicates")
    @Cacheable("employees")
    public List<ExcelExportDto> getEmployeesForExport(FilterDataModel filterData) {
        List<SubIndustry> industries = subIndustryService.getIndustries(filterData.getIndustry());
        Integer minEmployees = filterData.getMinEmployeesCount() == null ? 0 : filterData.getMinEmployeesCount();
        Integer maxEmployees = filterData.getMaxEmployeesCount() == null ? Integer.MAX_VALUE : filterData.getMaxEmployeesCount();
        Boolean includeCompaniesWithNoEmployeeData = filterData.getIncludeCompaniesWithNoEmployeeData();
        Integer yearFound = filterData.getYearFound();
        String city = filterData.getCity();
        String country = filterData.getCountry();

        return employeeRepository.getFilteredEmployees(industries, minEmployees, maxEmployees,
                includeCompaniesWithNoEmployeeData, yearFound, country, city)
                .stream()
                .map(EmployeeExportDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("Duplicates")
    @Cacheable("employees")
    public List<ExcelExportDto> getEmployeesWithCompaniesForExport(FilterDataModel filterData) {
        List<SubIndustry> industries = subIndustryService.getIndustries(filterData.getIndustry());
        Integer minEmployees = filterData.getMinEmployeesCount() == null ? 0 : filterData.getMinEmployeesCount();
        Integer maxEmployees = filterData.getMaxEmployeesCount() == null ? Integer.MAX_VALUE : filterData.getMaxEmployeesCount();
        Boolean includeCompaniesWithNoEmployeeData = filterData.getIncludeCompaniesWithNoEmployeeData();
        Integer yearFound = filterData.getYearFound();
        String city = filterData.getCity();
        String country = filterData.getCountry();

        return employeeRepository.getFilteredEmployees(industries, minEmployees, maxEmployees,
                includeCompaniesWithNoEmployeeData, yearFound, country, city)
                .stream()
                .map(this::mapToEmployeeWithCompanyDto)
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("Duplicates")
    @Cacheable("employees")
    public EmployeesPageVO getEmployeesPage(Pageable pageable, FilterDataModel filterData) {
        List<SubIndustry> industries = subIndustryService.getIndustries(filterData.getIndustry());
        Integer minEmployees = filterData.getMinEmployeesCount() == null ? 0 : filterData.getMinEmployeesCount();
        Integer maxEmployees = filterData.getMaxEmployeesCount() == null ? Integer.MAX_VALUE : filterData.getMaxEmployeesCount();
        Boolean includeCompaniesWithNoEmployeeData = filterData.getIncludeCompaniesWithNoEmployeeData();
        Integer yearFound = filterData.getYearFound();
        String city = filterData.getCity();
        String country = filterData.getCountry();

        List<EmployeeListVO> employees = employeeRepository.getFilteredEmployeesPage(pageable, industries, minEmployees, maxEmployees,
                includeCompaniesWithNoEmployeeData, yearFound, country, city)
                .stream()
                .map(e -> mapper.map(e, EmployeeListVO.class))
                .collect(Collectors.toList());

        long employeesCount = getFilteredEmployeesCount(filterData);

        EmployeesPageVO employeesPageVO = new EmployeesPageVO();
        employeesPageVO.setEmployees(employees);
        employeesPageVO.setTotalItemsCount(employeesCount);
        return employeesPageVO;
    }

    @Override
    @SuppressWarnings("Duplicates")
    @Cacheable("employees")
    public long getFilteredEmployeesCount(FilterDataModel filterData) {
        List<SubIndustry> industries = subIndustryService.getIndustries(filterData.getIndustry());
        Integer minEmployees = filterData.getMinEmployeesCount() == null ? 0 : filterData.getMinEmployeesCount();
        Integer maxEmployees = filterData.getMaxEmployeesCount() == null ? Integer.MAX_VALUE : filterData.getMaxEmployeesCount();
        Boolean includeCompaniesWithNoEmployeeData = filterData.getIncludeCompaniesWithNoEmployeeData();
        Integer yearFound = filterData.getYearFound();
        String city = filterData.getCity();
        String country = filterData.getCountry();

        return employeeRepository.getFilteredEmployeesCount(industries, minEmployees, maxEmployees,
                includeCompaniesWithNoEmployeeData, yearFound, country, city);
    }

    @Override
    public EmployeeDetailsVO getById(Long id) {
        return employeeRepository.findByIdEager(id)
                .map(c -> mapper.map(c, EmployeeDetailsVO.class))
                .orElseThrow(() -> new NotFoundException("No such employee."));
    }

    @Override
    @CacheEvict(cacheNames = {"excel", "companies", "employees"}, allEntries = true)
    public void saveEmployees(Collection<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    @Override
    public Map<String, Employee> getEmployeesByEmail(List<String> emails) {
        return employeeRepository.findByEmailIn(emails).stream()
                .collect(Collectors.toMap(Employee::getEmail, e -> e));
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeWithCompanyExportDto mapToEmployeeWithCompanyDto(Employee employee) {
        EmployeeWithCompanyExportDto exportDto = new EmployeeWithCompanyExportDto();
        exportDto.setEmployeeExportDto(new EmployeeExportDto(employee));
        exportDto.setCompanyExportDto(new CompanyExportDto(employee.getCompany()));
        return exportDto;
    }
}
