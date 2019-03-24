package softuni.aggregator.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.MajorIndustry;
import softuni.aggregator.domain.entities.MinorIndustry;
import softuni.aggregator.domain.model.vo.CompanyListVO;
import softuni.aggregator.domain.repository.CompanyRepository;
import softuni.aggregator.service.CompanyService;
import softuni.aggregator.service.excel.writer.model.CompaniesExportDto;
import softuni.aggregator.service.excel.writer.model.ExcelExportDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ExcelExportDto> getCompaniesForExport() {
        return mapToExcelDto(companyRepository.findAll());
    }

    @Override
    public List<CompanyListVO> getCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable).stream()
                .map(c -> modelMapper.map(c, CompanyListVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalCompaniesCount() {
        return companyRepository.count();
    }

    private List<ExcelExportDto> mapToExcelDto(List<Company> companies) {
        List<ExcelExportDto> companyDtos = new ArrayList<>();
        for (Company company : companies) {
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

            companyDtos.add(companyDto);
        }
        return companyDtos;
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
}
