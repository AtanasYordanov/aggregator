package softuni.aggregator.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.MajorIndustry;
import softuni.aggregator.domain.entities.MinorIndustry;
import softuni.aggregator.domain.model.binding.CompaniesFilterDataModel;
import softuni.aggregator.domain.model.vo.CompanyDetailsVO;
import softuni.aggregator.domain.model.vo.CompanyListVO;
import softuni.aggregator.domain.repository.CompanyRepository;
import softuni.aggregator.service.CompanyService;
import softuni.aggregator.service.MinorIndustryService;
import softuni.aggregator.service.excel.writer.model.CompaniesExportDto;
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
    public List<ExcelExportDto> getCompaniesForExport(CompaniesFilterDataModel filterData) {
        List<MinorIndustry> industries = minorIndustryService.getIndustries(filterData.getIndustry());

        if (!industries.isEmpty()) {
            return companyRepository.findAllByIndustryIn(industries).stream()
                    .map(this::mapToExcelDto)
                    .collect(Collectors.toList());
        } else {
            return companyRepository.findAll().stream()
                    .map(this::mapToExcelDto)
                    .collect(Collectors.toList());
        }
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

    private String getMajorIndustry(Company company) {
        MinorIndustry industry = company.getIndustry();
        if (industry == null) {
            return null;
        }
        MajorIndustry majorIndustry = industry.getMajorIndustry();
        return majorIndustry != null ? majorIndustry.getName() : null;
    }

    private String getMinorIndustry(Company company) {
        MinorIndustry industry = company.getIndustry();
        return industry != null ? industry.getName() : null;
    }

    private ExcelExportDto mapToExcelDto(Company company) {
        CompaniesExportDto companyDto = new CompaniesExportDto();

        companyDto.setName(company.getName());
        companyDto.setWebsite(company.getWebsite());
        companyDto.setPostcode(company.getPostcode());
        companyDto.setCity(company.getCity());
        companyDto.setCountry(company.getCountry());
        companyDto.setCompanyPhone(company.getCompanyPhone());
        companyDto.setCompanyEmails(String.join(System.lineSeparator(), company.getCompanyEmails()));
        companyDto.setXingIndustry1(getMajorIndustry(company));
        companyDto.setXingIndustry2(getMinorIndustry(company));
        companyDto.setEmployeesRange(company.getEmployeesRange());
        companyDto.setEmployeesPage(company.getEmployeesPage());
        companyDto.setStreet(company.getStreet());
        companyDto.setFax(company.getFax());
        companyDto.setInformation(company.getInformation());
        companyDto.setXingProfileLink(company.getXingProfileLink());
        companyDto.setYearFound(company.getYearFound());
        companyDto.setProductsAndServices(company.getProductsAndServices());
        companyDto.setVATNumber(company.getVATNumber());
        companyDto.setBvDIdNumber(company.getBvDIdNumber());
        companyDto.setISOCountryCode(company.getISOCountryCode());
        companyDto.setNaceRevMainSection(company.getNaceRevMainSection());
        companyDto.setNaceRevCoreCode(company.getNaceRevCoreCode());
        companyDto.setConsolidationCode(company.getConsolidationCode());
        companyDto.setOperatingIncome(company.getOperatingIncome());
        companyDto.setEmployeesCount(company.getEmployeesCount());
        companyDto.setAddress(company.getAddress());
        companyDto.setJobDescription(company.getJobDescription());
        companyDto.setStandardizedLegalForm(company.getStandardizedLegalForm());
        companyDto.setManagersCount(company.getManagersCount());
        companyDto.setCorporationCompaniesCount(company.getCorporationCompaniesCount());
        companyDto.setSubsidiariesCount(company.getSubsidiariesCount());

        return companyDto;
    }
}
