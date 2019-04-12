package softuni.aggregator.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.SubIndustry;
import softuni.aggregator.domain.model.binding.FilterDataModel;
import softuni.aggregator.domain.model.vo.CompanyDetailsVO;
import softuni.aggregator.domain.model.vo.CompanyListVO;
import softuni.aggregator.domain.model.vo.page.CompaniesPageVO;
import softuni.aggregator.domain.repository.CompanyRepository;
import softuni.aggregator.service.CompanyService;
import softuni.aggregator.service.SubIndustryService;
import softuni.aggregator.service.excel.writer.model.CompanyExportDto;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;
import org.springframework.transaction.annotation.Transactional;
import softuni.aggregator.web.exceptions.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final SubIndustryService subIndustryService;
    private final CompanyRepository companyRepository;
    private final ModelMapper mapper;

    @Autowired
    public CompanyServiceImpl(SubIndustryService subIndustryService,
                              CompanyRepository companyRepository, ModelMapper mapper) {
        this.subIndustryService = subIndustryService;
        this.companyRepository = companyRepository;
        this.mapper = mapper;
    }

    @Override
    @SuppressWarnings("Duplicates")
    @Cacheable("companies")
    public List<ExcelExportDto> getCompaniesForExport(FilterDataModel filterData) {
        List<SubIndustry> industries = subIndustryService.getIndustries(filterData.getIndustry());
        Integer minEmployees = filterData.getMinEmployeesCount() == null ? 0 : filterData.getMinEmployeesCount();
        Integer maxEmployees = filterData.getMaxEmployeesCount() == null ? Integer.MAX_VALUE : filterData.getMaxEmployeesCount();
        Boolean includeCompaniesWithNoEmployeeData = filterData.getIncludeCompaniesWithNoEmployeeData();
        Integer yearFound = filterData.getYearFound();
        String city = filterData.getCity();
        String country = filterData.getCountry();

        return companyRepository.getFilteredCompanies(industries, minEmployees, maxEmployees,
                includeCompaniesWithNoEmployeeData, yearFound, country, city)
                .stream()
                .map(CompanyExportDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("Duplicates")
    @Cacheable("companies")
    public CompaniesPageVO getCompaniesPage(Pageable pageable, FilterDataModel filterData) {
        List<SubIndustry> industries = subIndustryService.getIndustries(filterData.getIndustry());
        Integer minEmployees = filterData.getMinEmployeesCount() == null ? 0 : filterData.getMinEmployeesCount();
        Integer maxEmployees = filterData.getMaxEmployeesCount() == null ? Integer.MAX_VALUE : filterData.getMaxEmployeesCount();
        Boolean includeCompaniesWithNoEmployeeData = filterData.getIncludeCompaniesWithNoEmployeeData();
        Integer yearFound = filterData.getYearFound();
        String city = filterData.getCity();
        String country = filterData.getCountry();

        List<CompanyListVO> companies = companyRepository.getFilteredCompaniesPage(pageable, industries, minEmployees, maxEmployees,
                includeCompaniesWithNoEmployeeData, yearFound, country, city)
                .stream()
                .map(c -> mapper.map(c, CompanyListVO.class))
                .collect(Collectors.toList());

        long companiesCount = getFilteredCompaniesCount(filterData);

        CompaniesPageVO companiesPageVO = new CompaniesPageVO();
        companiesPageVO.setCompanies(companies);
        companiesPageVO.setTotalItemsCount(companiesCount);

        return companiesPageVO;
    }

    @Override
    @SuppressWarnings("Duplicates")
    @Cacheable("companies")
    public long getFilteredCompaniesCount(FilterDataModel filterData) {
        List<SubIndustry> industries = subIndustryService.getIndustries(filterData.getIndustry());
        Integer minEmployees = filterData.getMinEmployeesCount() == null ? 0 : filterData.getMinEmployeesCount();
        Integer maxEmployees = filterData.getMaxEmployeesCount() == null ? Integer.MAX_VALUE : filterData.getMaxEmployeesCount();
        Boolean includeCompaniesWithNoEmployeeData = filterData.getIncludeCompaniesWithNoEmployeeData();
        Integer yearFound = filterData.getYearFound();
        String city = filterData.getCity();
        String country = filterData.getCountry();

        return companyRepository.getFilteredCompaniesCount(industries, minEmployees, maxEmployees,
                includeCompaniesWithNoEmployeeData, yearFound, country, city);
    }

    @Override
    public CompanyDetailsVO getById(Long id) {
        return companyRepository.findById(id)
                .map(c -> mapper.map(c, CompanyDetailsVO.class))
                .orElseThrow(() -> new NotFoundException("No such company."));
    }

    @Override
    public void saveCompanies(Collection<Company> companies) {
        companyRepository.saveAll(companies);
    }

    @Override
    public Map<String, Company> getCompaniesByWebsite(List<String> companyWebsites) {
        return companyRepository.findAllByWebsiteIn(companyWebsites).stream()
                .collect(Collectors.toMap(Company::getWebsite, c -> c));
    }

    @Override
    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such company."));

        company.getEmployees().forEach(e -> e.setCompany(null));

        companyRepository.delete(company);
    }
}
