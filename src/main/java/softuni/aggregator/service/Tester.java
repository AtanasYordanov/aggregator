package softuni.aggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.service.api.CompanyService;
import softuni.aggregator.service.api.EmployeeService;

import javax.annotation.PostConstruct;

@Service
public class Tester {

    private final CompanyService companyService;
    private final EmployeeService employeeService;

    @Autowired
    public Tester(CompanyService companyService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    @PostConstruct
    public void init() {
        long start = System.currentTimeMillis();
        companyService.saveCompanyFromXing();
        long end = System.currentTimeMillis();
        System.out.println("Xing: " + (end - start));
        start = System.currentTimeMillis();
        companyService.saveCompanyFromOrbis();
        end = System.currentTimeMillis();
        System.out.println("Orbis: " + (end - start));
        start = System.currentTimeMillis();
        employeeService.saveEmployeesFromExcel();
        end = System.currentTimeMillis();
        System.out.println("Employees: " + (end - start));
    }
}
