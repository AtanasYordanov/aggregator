package softuni.aggregator.service.excel.reader.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CompanyImportDtoTests {

    private CompanyImportDto companyImportDto;

    @Before
    public void init() {
        companyImportDto = new XingCompanyImportDto();
    }

    @Test
    public void setWebsite_startsWithHttp_shouldTrimStart() {
        String website = "http://www.test.bg";
        String trimmed = "www.test.bg";
        companyImportDto.setWebsite(website);

        Assert.assertEquals(trimmed, companyImportDto.getWebsite());
    }

    @Test
    public void setWebsite_startsWithHttps_shouldTrimStart() {
        String website = "https://www.test.bg";
        String trimmed = "www.test.bg";
        companyImportDto.setWebsite(website);

        Assert.assertEquals(trimmed, companyImportDto.getWebsite());
    }

    @Test
    public void setWebsite_endsWithDash_shouldTrimEnd() {
        String website = "www.test.bg/";
        String trimmed = "www.test.bg";
        companyImportDto.setWebsite(website);

        Assert.assertEquals(trimmed, companyImportDto.getWebsite());
    }
}
