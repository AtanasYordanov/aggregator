package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.domain.model.vo.EmployeeListVO;
import softuni.aggregator.domain.model.vo.page.EmployeesPageVO;
import softuni.aggregator.service.EmployeeService;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeesController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/catalog")
    public ModelAndView employees(ModelAndView model) {
        model.setViewName("employees");
        return model;
    }

    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeesPageVO> getCompaniesPage(Pageable pageable) {
        List<EmployeeListVO> employees = employeeService.getEmployeesPage(pageable);
        long employeesCount = employeeService.getTotalEmployeesCount();

        EmployeesPageVO employeesPageVO = new EmployeesPageVO();
        employeesPageVO.setEmployees(employees);
        employeesPageVO.setTotalItemsCount(employeesCount);

        return new ResponseEntity<>(employeesPageVO, HttpStatus.OK);
    }
}
