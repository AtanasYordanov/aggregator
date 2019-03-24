package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.domain.model.vo.CompanyListVO;
import softuni.aggregator.service.CompanyService;

import  org.springframework.data.domain.Pageable;
import softuni.aggregator.service.MajorIndustryService;
import softuni.aggregator.service.MinorIndustryService;

import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompaniesController {

    private final CompanyService companyService;
    private final MinorIndustryService minorIndustryService;
    private final MajorIndustryService majorIndustryService;

    @Autowired
    public CompaniesController(CompanyService companyService, MinorIndustryService minorIndustryService,
                               MajorIndustryService majorIndustryService) {
        this.companyService = companyService;
        this.minorIndustryService = minorIndustryService;
        this.majorIndustryService = majorIndustryService;
    }

    @GetMapping
    public ModelAndView getCompanies(ModelAndView model, Pageable pageable) {
        List<CompanyListVO> companies = companyService.getCompanies(pageable);
        List<String> minorIndustries = minorIndustryService.getAllIndustries();
        List<String> majorIndustries = majorIndustryService.getAllIndustries();
        long companiesCount = companyService.getTotalCompaniesCount();

        model.addObject("minorIndustries", minorIndustries);
        model.addObject("majorIndustries", majorIndustries);
        model.addObject("companies", companies);
        model.addObject("companiesCount", companiesCount);

        model.setViewName("companies");
        return model;
    }
}
