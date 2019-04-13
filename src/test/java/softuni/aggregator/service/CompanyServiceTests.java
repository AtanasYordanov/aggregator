package softuni.aggregator.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.SubIndustry;
import softuni.aggregator.domain.model.binding.FilterDataModel;
import softuni.aggregator.domain.model.vo.CompanyDetailsVO;
import softuni.aggregator.domain.model.vo.page.CompaniesPageVO;
import softuni.aggregator.domain.repository.CompanyRepository;
import softuni.aggregator.service.impl.CompanyServiceImpl;
import softuni.aggregator.web.exceptions.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTests {

    @Mock
    private CompanyRepository mockCompanyRepository;

    @Mock
    private SubIndustryService mockSubIndustryService;

    private CompanyService companyService;

    @Before
    public void init() {
        ModelMapper mapper = new ModelMapper();
        companyService = new CompanyServiceImpl(mockCompanyRepository, mockSubIndustryService, mapper);
    }

    @Test
    public void getCompaniesForExport_parametersArePresent_shouldCallRepositoryMethodWithCorrectParameters() {
        FilterDataModel filterData = buildFilterData();
        List<SubIndustry> industries = List.of(new SubIndustry());
        Mockito.when(mockSubIndustryService.getIndustries(filterData.getIndustry())).thenReturn(industries);

        companyService.getCompaniesForExport(filterData);

        Mockito.verify(mockCompanyRepository).getFilteredCompanies(industries, filterData.getMinEmployeesCount(),
                filterData.getMaxEmployeesCount(), filterData.getIncludeCompaniesWithNoEmployeeData(),
                filterData.getYearFound(), filterData.getCountry(), filterData.getCity());
    }

    @Test
    public void getCompaniesForExport_missingParameters_shouldCallRepositoryMethodWithDefaultParameters() {
        FilterDataModel filterData = new FilterDataModel();
        List<SubIndustry> industries = List.of(new SubIndustry());
        Mockito.when(mockSubIndustryService.getIndustries(filterData.getIndustry())).thenReturn(industries);

        companyService.getCompaniesForExport(filterData);

        Mockito.verify(mockCompanyRepository).getFilteredCompanies(industries, 0,
                Integer.MAX_VALUE, null, null, null, null);
    }

    @Test
    public void getCompaniesPage_parametersArePresent_shouldCallRepositoryMethodWithCorrectParameters() {
        FilterDataModel filterData = buildFilterData();
        Pageable pageable = PageRequest.of(1, 20);
        List<SubIndustry> industries = List.of(new SubIndustry());

        Mockito.when(mockSubIndustryService.getIndustries(filterData.getIndustry())).thenReturn(industries);
        Mockito.when(mockCompanyRepository.getFilteredCompaniesPage(Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(List.of(new Company()));

        companyService.getCompaniesPage(pageable, filterData);

        Mockito.verify(mockCompanyRepository).getFilteredCompaniesPage(pageable, industries, filterData.getMinEmployeesCount(),
                filterData.getMaxEmployeesCount(), filterData.getIncludeCompaniesWithNoEmployeeData(),
                filterData.getYearFound(), filterData.getCountry(), filterData.getCity());
    }

    @Test
    public void getCompaniesPage_missingParameters_shouldCallRepositoryMethodWithDefaultParameters() {
        FilterDataModel filterData = new FilterDataModel();
        Pageable pageable = PageRequest.of(1, 20);
        List<SubIndustry> industries = List.of(new SubIndustry());

        Mockito.when(mockSubIndustryService.getIndustries(filterData.getIndustry())).thenReturn(industries);
        Mockito.when(mockCompanyRepository.getFilteredCompaniesPage(Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(List.of(new Company()));

        companyService.getCompaniesPage(pageable, filterData);

        Mockito.verify(mockCompanyRepository).getFilteredCompaniesPage(pageable, industries, 0,
                Integer.MAX_VALUE, null, null, null, null);
    }

    @Test
    public void getCompaniesPage_always_shouldFillPageVoCorrectly() {
        Long testCompaniesCount = 50L;
        FilterDataModel filterData = new FilterDataModel();
        Pageable pageable = PageRequest.of(1, 20);
        List<SubIndustry> industries = List.of(new SubIndustry());
        Company testCompany = new Company();
        testCompany.setWebsite("www.test.bg");
        List<Company> companies = List.of(testCompany);

        Mockito.when(mockSubIndustryService.getIndustries(filterData.getIndustry())).thenReturn(industries);
        Mockito.when(mockCompanyRepository.getFilteredCompaniesCount(Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(testCompaniesCount);
        Mockito.when(mockCompanyRepository.getFilteredCompaniesPage(Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(companies);

        CompaniesPageVO companiesPage = companyService.getCompaniesPage(pageable, filterData);


        Assert.assertEquals((long) testCompaniesCount, companiesPage.getTotalItemsCount());
        for (Company company : companies) {
            boolean containsCompany = companiesPage.getCompanies().stream()
                    .anyMatch(c -> c.getWebsite().equals(company.getWebsite()));
            Assert.assertTrue(containsCompany);
        }
    }

    @Test
    public void getById_validId_shouldReturnCorrectCompanyView() {
        Long testId = 1L;

        Company company = new Company();
        company.setWebsite("www.cc.bb");
        company.setCity("some city");
        company.setCountry("some country");
        company.setName("some name");

        Mockito.when(mockCompanyRepository.findById(testId)).thenReturn(Optional.of(company));

        CompanyDetailsVO companyDetailsVO = companyService.getById(testId);

        Assert.assertEquals(company.getWebsite(), companyDetailsVO.getWebsite());
        Assert.assertEquals(company.getCity(), companyDetailsVO.getCity());
        Assert.assertEquals(company.getCountry(), companyDetailsVO.getCountry());
        Assert.assertEquals(company.getName(), companyDetailsVO.getName());
    }

    @Test(expected = NotFoundException.class)
    public void getById_nonexistentCompany_shouldThrowNotFoundException() {
        Long testId = 1L;
        Mockito.when(mockCompanyRepository.findById(testId)).thenReturn(Optional.empty());

        companyService.getById(testId);
    }

    @Test
    public void saveCompanies_always_shouldCallCorrectRepositoryMethod() {
        List<Company> companies = List.of(new Company());
        companyService.saveCompanies(companies);

        Mockito.verify(mockCompanyRepository).saveAll(companies);
    }

    @Test
    public void getCompaniesByWebsite_always_shouldReturnCorrectCompanyKeyValueMap() {
        String testWebsite1 = "www.test.bg";
        String testWebsite2 = "www.company.com";
        String testWebsite3 = "www.another.de";

        List<String> companyWebsites = List.of(testWebsite1, testWebsite2, testWebsite3);

        Company company1 = new Company();
        company1.setWebsite(testWebsite1);
        Company company2 = new Company();
        company2.setWebsite(testWebsite2);
        Company company3 = new Company();
        company3.setWebsite(testWebsite3);

        List<Company> companies = List.of(company1, company2, company3);

        Mockito.when(mockCompanyRepository.findAllByWebsiteIn(companyWebsites)).thenReturn(companies);

        Map<String, Company> companiesByWebsite = companyService.getCompaniesByWebsite(companyWebsites);

        Assert.assertEquals(companies.size(), companiesByWebsite.size());
        for (Company company : companies) {
            Assert.assertEquals(company, companiesByWebsite.get(company.getWebsite()));
        }
    }

    @Test
    public void deleteCompany_validId_shouldCallCorrectRepositoryMethod() {
        Long testId = 1L;
        Company company = new Company();
        company.setEmployees(List.of());
        Mockito.when(mockCompanyRepository.findById(testId)).thenReturn(Optional.of(company));

        companyService.deleteCompany(testId);

        Mockito.verify(mockCompanyRepository).delete(company);
    }

    @Test(expected = NotFoundException.class)
    public void deleteCompany_nonexistentCompany_shouldThrowNotFoundException() {
        Long testId = 1L;
        Mockito.when(mockCompanyRepository.findById(testId)).thenReturn(Optional.empty());

        companyService.deleteCompany(testId);
    }

    private FilterDataModel buildFilterData() {
        FilterDataModel filterData = new FilterDataModel();
        filterData.setIndustry("someIndustry");
        filterData.setMinEmployeesCount(1);
        filterData.setMaxEmployeesCount(100);
        filterData.setYearFound(2010);
        filterData.setCity("Sofia");
        filterData.setCountry("Bulgaria");
        filterData.setIncludeCompaniesWithNoEmployeeData(true);
        return filterData;
    }
}
