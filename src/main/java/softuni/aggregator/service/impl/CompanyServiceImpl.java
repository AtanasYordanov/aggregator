package softuni.aggregator.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.MajorIndustry;
import softuni.aggregator.domain.entities.MinorIndustry;
import softuni.aggregator.domain.model.binding.CompaniesFilterDataModel;
import softuni.aggregator.domain.model.vo.CompanyListVO;
import softuni.aggregator.domain.repository.CompanyRepository;
import softuni.aggregator.service.CompanyService;
import softuni.aggregator.service.MajorIndustryService;
import softuni.aggregator.service.MinorIndustryService;
import softuni.aggregator.service.excel.writer.model.CompaniesExportDto;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private static final String MAJOR_INDUSTRY_PREFIX = "Maj:";
    private static final String MINOR_INDUSTRY_PREFIX = "Min:";
    private static final String ALL_INDUSTRIES = "all";

    private final MinorIndustryService minorIndustryService;
    private final MajorIndustryService majorIndustryService;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyServiceImpl(MinorIndustryService minorIndustryService, MajorIndustryService majorIndustryService,
                              CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.minorIndustryService = minorIndustryService;
        this.majorIndustryService = majorIndustryService;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ExcelExportDto> getCompaniesForExport(CompaniesFilterDataModel filterData) {
        List<MinorIndustry> industries = getIndustries(filterData.getIndustry());

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
    public List<CompanyListVO> getCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable).stream()
                .map(c -> modelMapper.map(c, CompanyListVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CompanyListVO> getCompaniesPage(Pageable pageable, CompaniesFilterDataModel filterData) {
        String industry = filterData.getIndustry();
        List<MinorIndustry> industries = getIndustries(industry);

        if (!industries.isEmpty()) {
            return companyRepository.getCompaniesPageForIndustry(pageable, industries).stream()
                    .map(c -> modelMapper.map(c, CompanyListVO.class))
                    .collect(Collectors.toList());
        } else {
            return getCompanies(pageable);
        }
    }

    @Override
    public long getTotalCompaniesCount() {
        return companyRepository.count();
    }

    @Override
    public long getCompaniesCountForIndustry(String industry) {
        List<MinorIndustry> industries = getIndustries(industry);

        if (!industries.isEmpty()) {
            return companyRepository.getCompaniesCountForIndustry(industries);
        } else {
            return getTotalCompaniesCount();
        }
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

    private List<MinorIndustry> getIndustries(String industryName) {
        List<MinorIndustry> industries = new ArrayList<>();

        if (industryName == null || industryName.equals(ALL_INDUSTRIES)) {
            return industries;
        } else if (industryName.startsWith(MAJOR_INDUSTRY_PREFIX)) {
            MajorIndustry majorIndustry = majorIndustryService.getMajorIndustryByName(industryName.substring(4));
            industries.addAll(minorIndustryService.getAllIndustriesForMajor(majorIndustry));
        } else if (industryName.startsWith(MINOR_INDUSTRY_PREFIX)) {
            industries.add(minorIndustryService.getIndustryByName(industryName.substring(4)));
        }
        return industries;
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
        companyDto.setStreet(company.getStreet());
        companyDto.setFax(company.getFax());
        companyDto.setInformation(company.getInformation());
        companyDto.setCompanyProfileLink(company.getCompanyProfileLink());
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
