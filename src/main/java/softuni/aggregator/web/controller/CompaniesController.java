package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.domain.model.binding.CompaniesFilterDataModel;
import softuni.aggregator.domain.model.vo.CompanyListVO;
import softuni.aggregator.domain.model.vo.page.CompaniesPageVO;
import softuni.aggregator.service.CompanyService;

import org.springframework.data.domain.Pageable;
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

    @GetMapping("/catalog")
    public ModelAndView getCompaniesView(ModelAndView model) {
        model.setViewName("companies");
        return model;
    }

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompaniesPageVO> getCompaniesData(Pageable pageable) {
        List<CompanyListVO> companies = companyService.getCompanies(pageable);
        List<String> minorIndustries = minorIndustryService.getAllIndustryNames();
        List<String> majorIndustries = majorIndustryService.getAllIndustryNames();
        long companiesCount = companyService.getTotalCompaniesCount();

        CompaniesPageVO companiesPageVO = new CompaniesPageVO();
        companiesPageVO.setCompanies(companies);
        companiesPageVO.setMinorIndustries(minorIndustries);
        companiesPageVO.setMajorIndustries(majorIndustries);
        companiesPageVO.setTotalItemsCount(companiesCount);

        return new ResponseEntity<>(companiesPageVO, HttpStatus.OK);
    }

    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompaniesPageVO> getCompaniesPage(Pageable pageable, CompaniesFilterDataModel filterData) {
        List<CompanyListVO> companies = companyService.getCompaniesPage(pageable, filterData);
        long companiesCount = companyService.getCompaniesCountForIndustry(filterData.getIndustry());

        CompaniesPageVO companiesPageVO = new CompaniesPageVO();
        companiesPageVO.setCompanies(companies);
        companiesPageVO.setTotalItemsCount(companiesCount);

        return new ResponseEntity<>(companiesPageVO, HttpStatus.OK);
    }
}
