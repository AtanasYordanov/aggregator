package softuni.aggregator.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.SubIndustry;
import softuni.aggregator.domain.model.binding.CompaniesFilterDataModel;
import softuni.aggregator.domain.model.vo.CompanyDetailsVO;
import softuni.aggregator.domain.model.vo.CompanyListVO;
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
    public List<ExcelExportDto> getCompaniesForExport(CompaniesFilterDataModel filterData) {
        List<SubIndustry> industries = subIndustryService.getIndustries(filterData.getIndustry());
        return companyRepository.getFilteredCompanies(industries).stream()
                .map(CompanyExportDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompanyListVO> getCompaniesPage(Pageable pageable, CompaniesFilterDataModel filterData) {
        List<SubIndustry> industries = subIndustryService.getIndustries(filterData.getIndustry());
        return companyRepository.getFilteredCompaniesPage(pageable, industries).stream()
                .map(c -> mapper.map(c, CompanyListVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public long getFilteredCompaniesCount(CompaniesFilterDataModel filterData) {
        List<SubIndustry> industries = subIndustryService.getIndustries(filterData.getIndustry());
        return companyRepository.getFilteredCompaniesCount(industries);
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
