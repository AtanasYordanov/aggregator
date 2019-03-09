package softuni.aggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
//        companyService.saveCompanyFromXing();
        companyService.saveCompanyFromOrbis();
//        employeeService.saveEmployeesFromExcel();
    }
}
