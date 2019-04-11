package softuni.aggregator.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Employee;
import softuni.aggregator.domain.entities.MinorIndustry;
import softuni.aggregator.domain.model.binding.EmployeesFilterDataModel;
import softuni.aggregator.domain.model.vo.EmployeeDetailsVO;
import softuni.aggregator.domain.model.vo.EmployeeListVO;
import softuni.aggregator.domain.repository.EmployeeRepository;
import softuni.aggregator.service.EmployeeService;
import softuni.aggregator.service.MinorIndustryService;
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
    private final MinorIndustryService minorIndustryService;
    private final ModelMapper mapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, MinorIndustryService minorIndustryService, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.minorIndustryService = minorIndustryService;
        this.mapper = mapper;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public List<ExcelExportDto> getEmployeesForExport(EmployeesFilterDataModel filterData) {
        List<MinorIndustry> industries = minorIndustryService.getIndustries(filterData.getIndustry());

        if (!industries.isEmpty()) {
            return employeeRepository.findAllByIndustryIn(industries).stream()
                    .map(EmployeeExportDto::new)
                    .collect(Collectors.toList());
        }
        return employeeRepository.findAll().stream()
                .map(EmployeeExportDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExcelExportDto> getEmployeesWithCompaniesForExport(EmployeesFilterDataModel filterData) {
        List<MinorIndustry> industries = minorIndustryService.getIndustries(filterData.getIndustry());

        if (!industries.isEmpty()) {
            return employeeRepository.findAllByIndustryInEager(industries).stream()
                    .filter(e -> e.getCompany() != null)
                    .map(this::mapToEmployeeWithCompanyDto)
                    .collect(Collectors.toList());
        }
        return employeeRepository.findAllEager().stream()
                .filter(e -> e.getCompany() != null)
                .map(this::mapToEmployeeWithCompanyDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeListVO> getEmployeesPage(Pageable pageable, EmployeesFilterDataModel filterData) {
        String industry = filterData.getIndustry();
        List<MinorIndustry> industries = minorIndustryService.getIndustries(industry);

        if (!industries.isEmpty()) {
            return employeeRepository.getEmployeesPageForIndustry(pageable, industries).stream()
                    .map(e -> mapper.map(e, EmployeeListVO.class))
                    .collect(Collectors.toList());
        }

        return employeeRepository.findAll(pageable).stream()
                .map(e ->  mapper.map(e, EmployeeListVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalEmployeesCount() {
        return employeeRepository.count();
    }

    @Override
    public long getEmployeesCountForIndustry(String industry) {
        List<MinorIndustry> industries = minorIndustryService.getIndustries(industry);

        if (!industries.isEmpty()) {
            return employeeRepository.getCompaniesCountForIndustry(industries);
        } else {
            return getTotalEmployeesCount();
        }
    }

    @Override
    public EmployeeDetailsVO getById(Long id) {
        return employeeRepository.findByIdEager(id)
                .map(c -> mapper.map(c, EmployeeDetailsVO.class))
                .orElseThrow(() -> new NotFoundException("No such employee."));
    }

    @Override
    public void saveEmployees(Collection<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    @Override
    public Map<String, Employee> getEmployeesByEmail(List<String> emails) {
        return employeeRepository.findByEmailIn(emails).stream()
                .collect(Collectors.toMap(Employee::getEmail, e -> e));
    }

    private EmployeeWithCompanyExportDto mapToEmployeeWithCompanyDto(Employee employee) {
        EmployeeWithCompanyExportDto exportDto = new EmployeeWithCompanyExportDto();
        exportDto.setEmployeeExportDto(new EmployeeExportDto(employee));
        exportDto.setCompanyExportDto(new CompanyExportDto(employee.getCompany()));
        return exportDto;
    }
}
