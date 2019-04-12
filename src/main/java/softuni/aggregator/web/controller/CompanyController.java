package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.domain.model.binding.FilterDataModel;
import softuni.aggregator.domain.model.vo.CompanyDetailsVO;
import softuni.aggregator.domain.model.vo.page.CompaniesPageVO;
import softuni.aggregator.service.CompanyService;

import org.springframework.data.domain.Pageable;
import softuni.aggregator.service.MainIndustryService;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final MainIndustryService mainIndustryService;

    @Autowired
    public CompanyController(CompanyService companyService, MainIndustryService mainIndustryService) {
        this.companyService = companyService;
        this.mainIndustryService = mainIndustryService;
    }

    @GetMapping("/catalog")
    public ModelAndView companies(ModelAndView model) {
        model.setViewName("companies");
        return model;
    }

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompaniesPageVO> getCompaniesData(Pageable pageable, FilterDataModel filterData) {
        CompaniesPageVO companiesPageVO = companyService.getCompaniesPage(pageable, filterData);
        mainIndustryService.fillFilterPageVO(companiesPageVO);
        return new ResponseEntity<>(companiesPageVO, HttpStatus.OK);
    }

    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompaniesPageVO> getCompaniesPage(Pageable pageable, FilterDataModel filterData) {
        CompaniesPageVO companiesPageVO =  companyService.getCompaniesPage(pageable, filterData);
        return new ResponseEntity<>(companiesPageVO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ModelAndView getCompanyDetails(ModelAndView model, @PathVariable Long id) {
        CompanyDetailsVO company = companyService.getById(id);
        model.addObject("company", company);
        model.setViewName("company-details");
        return model;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_ROOT_ADMIN", "ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }
}
