package softuni.aggregator.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.MinorIndustry;
import softuni.aggregator.domain.model.binding.CompaniesFilterDataModel;
import softuni.aggregator.domain.model.vo.CompanyDetailsVO;
import softuni.aggregator.domain.model.vo.CompanyListVO;
import softuni.aggregator.domain.repository.CompanyRepository;
import softuni.aggregator.service.CompanyService;
import softuni.aggregator.service.MinorIndustryService;
import softuni.aggregator.service.excel.writer.model.CompanyExportDto;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;
import org.springframework.transaction.annotation.Transactional;
import softuni.aggregator.web.exceptions.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final MinorIndustryService minorIndustryService;
    private final CompanyRepository companyRepository;
    private final ModelMapper mapper;

    @Autowired
    public CompanyServiceImpl(MinorIndustryService minorIndustryService,
                              CompanyRepository companyRepository, ModelMapper mapper) {
        this.minorIndustryService = minorIndustryService;
        this.companyRepository = companyRepository;
        this.mapper = mapper;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public List<ExcelExportDto> getCompaniesForExport(CompaniesFilterDataModel filterData) {
        List<MinorIndustry> industries = minorIndustryService.getIndustries(filterData.getIndustry());

        if (!industries.isEmpty()) {
            return companyRepository.findAllByIndustryIn(industries).stream()
                    .map(CompanyExportDto::new)
                    .collect(Collectors.toList());
        }
        return companyRepository.findAll().stream()
                .map(CompanyExportDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompanyListVO> getCompaniesPage(Pageable pageable, CompaniesFilterDataModel filterData) {
        String industry = filterData.getIndustry();
        List<MinorIndustry> industries = minorIndustryService.getIndustries(industry);

        if (!industries.isEmpty()) {
            return companyRepository.getCompaniesPageForIndustry(pageable, industries).stream()
                    .map(c -> mapper.map(c, CompanyListVO.class))
                    .collect(Collectors.toList());
        }

        return companyRepository.findAll(pageable).stream()
                .map(c -> mapper.map(c, CompanyListVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalCompaniesCount() {
        return companyRepository.count();
    }

    @Override
    public long getCompaniesCountForIndustry(String industry) {
        List<MinorIndustry> industries = minorIndustryService.getIndustries(industry);

        if (!industries.isEmpty()) {
            return companyRepository.getCompaniesCountForIndustry(industries);
        } else {
            return getTotalCompaniesCount();
        }
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
    public Company findByName(String companyName) {
        return companyRepository.findByName(companyName).orElse(null);
    }
}
