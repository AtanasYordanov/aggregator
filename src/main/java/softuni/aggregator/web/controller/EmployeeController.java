package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.domain.model.binding.EmployeesFilterDataModel;
import softuni.aggregator.domain.model.vo.EmployeeDetailsVO;
import softuni.aggregator.domain.model.vo.EmployeeListVO;
import softuni.aggregator.domain.model.vo.page.EmployeesPageVO;
import softuni.aggregator.service.EmployeeService;
import softuni.aggregator.service.MajorIndustryService;
import softuni.aggregator.service.MinorIndustryService;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final MinorIndustryService minorIndustryService;
    private final MajorIndustryService majorIndustryService;

    @Autowired
    public EmployeeController(EmployeeService employeeService,
                              MinorIndustryService minorIndustryService,
                              MajorIndustryService majorIndustryService) {
        this.employeeService = employeeService;
        this.minorIndustryService = minorIndustryService;
        this.majorIndustryService = majorIndustryService;
    }

    @GetMapping("/catalog")
    public ModelAndView employees(ModelAndView model) {
        model.setViewName("employees");
        return model;
    }

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeesPageVO> getEmployeesData(Pageable pageable, EmployeesFilterDataModel filterData) {
        List<EmployeeListVO> employees = employeeService.getEmployeesPage(pageable, filterData);
        List<String> minorIndustries = minorIndustryService.getAllIndustryNames();
        List<String> majorIndustries = majorIndustryService.getAllIndustryNames();
        long employeesCount = employeeService.getTotalEmployeesCount();

        EmployeesPageVO employeesPageVO = new EmployeesPageVO();
        employeesPageVO.setEmployees(employees);
        employeesPageVO.setMinorIndustries(minorIndustries);
        employeesPageVO.setMajorIndustries(majorIndustries);
        employeesPageVO.setTotalItemsCount(employeesCount);

        return new ResponseEntity<>(employeesPageVO, HttpStatus.OK);
    }

    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeesPageVO> getEmployeesPage(Pageable pageable, EmployeesFilterDataModel filterData) {
        List<EmployeeListVO> employees = employeeService.getEmployeesPage(pageable, filterData);
        long employeesCount = employeeService.getEmployeesCountForIndustry(filterData.getIndustry());

        EmployeesPageVO employeesPageVO = new EmployeesPageVO();
        employeesPageVO.setEmployees(employees);
        employeesPageVO.setTotalItemsCount(employeesCount);

        return new ResponseEntity<>(employeesPageVO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ModelAndView getEmployeeDetails(ModelAndView model, @PathVariable Long id) {
        EmployeeDetailsVO employee = employeeService.getById(id);
        model.addObject("employee", employee);
        model.setViewName("employee-details");
        return model;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_ROOT_ADMIN", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}
