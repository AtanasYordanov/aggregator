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
import softuni.aggregator.domain.model.vo.page.FilterPageVO;
import softuni.aggregator.domain.repository.MainIndustryRepository;
import softuni.aggregator.service.impl.MainIndustryServiceImpl;
import softuni.aggregator.web.exceptions.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MainIndustryServiceTests {

    @Mock
    private MainIndustryRepository mockMainIndustryRepository;

    @Mock
    private SubIndustryService mockSubIndustryService;

    private MainIndustryService mainIndustryService;

    @Before
    public void init() {
        mainIndustryService = new MainIndustryServiceImpl(mockMainIndustryRepository, mockSubIndustryService);
    }

    @Test
    public void getAllIndustriesByName_always_shouldReturnCorrectKeyValueMap() {
        List<MainIndustry> industries = buildTestMainIndustries();
        Mockito.when(mockMainIndustryRepository.findAll()).thenReturn(industries);

        Map<String, MainIndustry> allIndustriesByName = mainIndustryService.getAllIndustriesByName();

        Assert.assertEquals(industries.size(), allIndustriesByName.size());
        for (MainIndustry industry : industries) {
            Assert.assertEquals(industry, allIndustriesByName.get(industry.getName()));
        }
    }

    @Test
    public void getMainIndustryByName_validIndustryName_shouldReturnIndustry() {
        String industryName = "testName";
        MainIndustry mainIndustry = new MainIndustry(industryName);
        Mockito.when(mockMainIndustryRepository.findByName(industryName)).thenReturn(Optional.of(mainIndustry));

        MainIndustry receivedIndustry = mainIndustryService.getMainIndustryByName(industryName);

        Assert.assertEquals(mainIndustry, receivedIndustry);
    }

    @Test(expected = NotFoundException.class)
    public void getMainIndustryByName_nonexistentIndustry_shouldThrowNotFoundException() {
        String industryName = "testName";
        Mockito.when(mockMainIndustryRepository.findByName(industryName)).thenReturn(Optional.empty());

        mainIndustryService.getMainIndustryByName(industryName);
    }

    @Test
    public void fillFilterPageVO_always_shouldFillPageVoCorrectly() {
        List<MainIndustry> mainIndustries = buildTestMainIndustries();

        String testSubIndustry1 = "sunIndustry1";
        String testSubIndustry2 = "sunIndustry2";
        String testSubIndustry3 = "sunIndustry3";
        List<String> subIndustryNames = List.of(testSubIndustry1, testSubIndustry2, testSubIndustry3);

        Mockito.when(mockMainIndustryRepository.findAll()).thenReturn(mainIndustries);
        Mockito.when(mockSubIndustryService.getAllIndustryNames()).thenReturn(subIndustryNames);

        FilterPageVO filterPageVO = new FilterPageVO();
        mainIndustryService.fillFilterPageVO(filterPageVO);

        Assert.assertEquals(mainIndustries.size(), filterPageVO.getMainIndustries().size());
        Assert.assertEquals(subIndustryNames.size(), filterPageVO.getSubIndustries().size());

        for (MainIndustry mainIndustry : mainIndustries) {
            Assert.assertTrue(filterPageVO.getMainIndustries().stream()
                    .anyMatch(name -> mainIndustry.getName().equals(name)));
        }

        for (String subIndustryName: subIndustryNames) {
            Assert.assertTrue(filterPageVO.getSubIndustries().contains(subIndustryName));
        }
    }

    private List<MainIndustry> buildTestMainIndustries() {
        String testName1 = "mainIndustry1";
        String testName2 = "mainIndustry2";
        String testName3 = "mainIndustry3";

        MainIndustry mainIndustry1 = new MainIndustry();
        mainIndustry1.setName(testName1);

        MainIndustry mainIndustry2 = new MainIndustry();
        mainIndustry2.setName(testName2);

        MainIndustry mainIndustry3 = new MainIndustry();
        mainIndustry3.setName(testName3);

        return List.of(mainIndustry1, mainIndustry2, mainIndustry3);
    }
}
