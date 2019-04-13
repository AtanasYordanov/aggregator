package softuni.aggregator.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import softuni.aggregator.domain.entities.MainIndustry;
import softuni.aggregator.domain.entities.SubIndustry;
import softuni.aggregator.domain.repository.SubIndustryRepository;
import softuni.aggregator.service.impl.SubIndustryServiceImpl;
import softuni.aggregator.web.exceptions.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SubIndustryServiceTests {

    private static final String MAIN_INDUSTRY_PREFIX = "Main:";
    private static final String SUB_INDUSTRY_PREFIX = "Sub:";
    private static final String ALL_INDUSTRIES = "all";

    @Mock
    private SubIndustryRepository mockSubIndustryRepository;

    @Mock
    private MainIndustryService mockMainIndustryService;

    private SubIndustryService subIndustryService;

    @Before
    public void init() {
        subIndustryService = new SubIndustryServiceImpl(mockSubIndustryRepository, mockMainIndustryService);
    }

    @Test
    public void getAllIndustryNames_always_shouldReturnCorrectValues() {
        List<SubIndustry> industries = buildTestIndustries();
        Mockito.when(mockSubIndustryRepository.findAll()).thenReturn(industries);

        List<String> allIndustryNames = subIndustryService.getAllIndustryNames();

        Assert.assertEquals(industries.size(), allIndustryNames.size());
        Assert.assertEquals(industries.get(0).getName(), allIndustryNames.get(0));
        Assert.assertEquals(industries.get(1).getName(), allIndustryNames.get(1));
        Assert.assertEquals(industries.get(2).getName(), allIndustryNames.get(2));
    }

    @Test
    public void getAllIndustriesByName_always_shouldReturnCorrectKeyValueMap() {
        List<SubIndustry> industries = buildTestIndustries();
        Mockito.when(mockSubIndustryRepository.findAll()).thenReturn(industries);

        Map<String, SubIndustry> allIndustriesByName = subIndustryService.getAllIndustriesByName();

        Assert.assertEquals(industries.size(), allIndustriesByName.size());
        for (SubIndustry industry : industries) {
            Assert.assertEquals(industry, allIndustriesByName.get(industry.getName()));
        }
    }

    @Test
    public void getIndustries_nullIndustry_shouldReturnNull() {
        List<SubIndustry> industries = subIndustryService.getIndustries(null);

        Assert.assertNull(industries);
    }

    @Test
    public void getIndustries_allIndustries_shouldReturnNull() {
        List<SubIndustry> industries = subIndustryService.getIndustries(ALL_INDUSTRIES);

        Assert.assertNull(industries);
    }

    @Test
    public void getIndustries_mainIndustry_shouldReturnCorrectSubIndustries() {
        String industryName = "testMainIndustry";

        SubIndustry subIndustry1 = new SubIndustry();
        subIndustry1.setName("sub1");
        SubIndustry subIndustry2 = new SubIndustry();
        subIndustry2.setName("sub2");

        MainIndustry mainIndustry = new MainIndustry(industryName);
        List<SubIndustry> testIndustries = List.of(subIndustry1, subIndustry2);
        mainIndustry.setSubIndustries(List.of(subIndustry1, subIndustry2));

        Mockito.when(mockMainIndustryService.getMainIndustryByName(industryName)).thenReturn(mainIndustry);

        List<SubIndustry> industries = subIndustryService.getIndustries(MAIN_INDUSTRY_PREFIX + industryName);

        Assert.assertEquals(testIndustries.size(), industries.size());

        for (SubIndustry industry : industries) {
            Assert.assertTrue(testIndustries.contains(industry));
        }
    }

    @Test
    public void getIndustries_subIndustry_shouldReturnCorrectSubIndustry() {
        String industryName = "testSubIndustry";

        SubIndustry subIndustry = new SubIndustry();
        subIndustry.setName("testIndustry");
        Mockito.when(mockSubIndustryRepository.findByName(industryName)).thenReturn(Optional.of(subIndustry));

        List<SubIndustry> industries = subIndustryService.getIndustries(SUB_INDUSTRY_PREFIX + industryName);

        Assert.assertEquals(subIndustry, industries.get(0));
    }

    @Test(expected = NotFoundException.class)
    public void getIndustries_nonexistentSubIndustry_shouldThrowNotFoundException() {
        String industryName = "testSubIndustry";

        Mockito.when(mockSubIndustryRepository.findByName(industryName)).thenReturn(Optional.empty());

        subIndustryService.getIndustries(SUB_INDUSTRY_PREFIX + industryName);
    }

    private List<SubIndustry> buildTestIndustries() {
        String testName1 = "name1";
        String testName2 = "name2";
        String testName3 = "name3";

        SubIndustry subIndustry1 = new SubIndustry();
        subIndustry1.setName(testName1);

        SubIndustry subIndustry2 = new SubIndustry();
        subIndustry2.setName(testName2);

        SubIndustry subIndustry3 = new SubIndustry();
        subIndustry3.setName(testName3);

        return List.of(subIndustry1, subIndustry2, subIndustry3);
    }
}
