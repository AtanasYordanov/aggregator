package softuni.aggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.Employee;
import softuni.aggregator.domain.repository.CompanyRepository;
import softuni.aggregator.domain.repository.EmployeeRepository;
import softuni.aggregator.utils.excelreader.ExcelReader;
import softuni.aggregator.utils.excelreader.model.EmployeesExcelDto;
import softuni.aggregator.utils.excelreader.readers.EmployeesExcelReader;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveEmployeesFromExcel() {
        ExcelReader reader = new EmployeesExcelReader();

        String excelFilePath = "src\\main\\resources\\static\\Employees sample.xlsx";

        List<EmployeesExcelDto> data = reader.readExcel(excelFilePath);
        List<Employee> employees = new ArrayList<>();

        for (EmployeesExcelDto employeeDto : data) {
            Employee employee = employeeRepository.findByEmail(employeeDto.getEmail())
                    .orElse(new Employee());

            setProperties(employeeDto, employee);
            employees.add(employee);
        }

        employeeRepository.saveAll(employees);
    }

    private void setProperties(EmployeesExcelDto employeeDto, Employee employee) {
        Company company = companyRepository.findByName(employeeDto.getCompanyName())
                .orElse(null);

        employee.setCompany(company);
        employee.setEmail(employeeDto.getEmail());
        employee.setFullName(employeeDto.getFullName());
        employee.setHunterIoScore(employeeDto.getHunterIoScore() != null
                ? Double.valueOf(employeeDto.getHunterIoScore()).intValue()
                : null);
        employee.setPosition(employeeDto.getPosition());
    }
}
