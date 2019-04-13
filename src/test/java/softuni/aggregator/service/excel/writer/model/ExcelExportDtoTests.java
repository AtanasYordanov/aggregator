package softuni.aggregator.service.excel.writer.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import softuni.aggregator.service.excel.writer.columns.CompaniesExportColumn;
import softuni.aggregator.service.excel.writer.columns.EmployeesExportColumn;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ExcelExportDtoTests {

    private EmployeeExportDto employeeExportDto;
    private CompanyExportDto companyExportDto;

    @Before
    public void init() {
        employeeExportDto = new EmployeeExportDto();
        companyExportDto = new CompanyExportDto();
    }

    @Test
    public void getProperty_employeeFullName_shouldReturnCorrectGetter() {
        String testValue = "test value";
        employeeExportDto.setFullName(testValue);
        EmployeesExportColumn testColumn = EmployeesExportColumn.FULL_NAME;

        Object property = employeeExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_employeeEmail_shouldReturnCorrectGetter() {
        String testValue = "test value";
        employeeExportDto.setEmail(testValue);
        EmployeesExportColumn testColumn = EmployeesExportColumn.EMAIL;

        Object property = employeeExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_employeeHunterIoScore_shouldReturnCorrectGetter() {
        int testValue = 50;
        employeeExportDto.setHunterIoScore(testValue);
        EmployeesExportColumn testColumn = EmployeesExportColumn.HUNTER_IO_SCORE;

        Object property = employeeExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_employeePosition_shouldReturnCorrectGetter() {
        String testValue = "test value";
        employeeExportDto.setPosition(testValue);
        EmployeesExportColumn testColumn = EmployeesExportColumn.POSITION;

        Object property = employeeExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyName_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setName(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.COMPANY_NAME;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyWebsite_shouldReturnCorrectGetter() {
        String testValue = "test website";
        companyExportDto.setWebsite(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.WEBSITE;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyPostcode_shouldReturnCorrectGetter() {
        int testValue = 2323;
        companyExportDto.setPostcode(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.POSTCODE;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyCountry_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setCountry(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.COUNTRY;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyCompanyPhone_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setCompanyPhone(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.COMPANY_PHONE;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyCompanyEmails_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setCompanyEmails(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.COMPANY_EMAIL;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyXingIndustry1_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setXingIndustry1(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.XING_INDUSTRY_1;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyXingIndustry2_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setXingIndustry2(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.XING_INDUSTRY_2;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyStreet_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setStreet(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.STREET;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyFax_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setFax(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.FAX;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyInformation_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setInformation(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.INFORMATION;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyEmployeesPage_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setEmployeesPage(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.EMPLOYEES_PAGE;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyXingProfileLink_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setXingProfileLink(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.COMPANY_PROFILE_LINK;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyEmployeesRange_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setEmployeesRange(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.EMPLOYEES_RANGE;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyYearFound_shouldReturnCorrectGetter() {
        int testValue = 2010;
        companyExportDto.setYearFound(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.YEAR_FOUND;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyProductsAndServices_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setProductsAndServices(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.PRODUCTS_AND_SERVICES;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyVATNumber_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setVATNumber(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.VAT_NUMBER;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyBvDIdNumber_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setBvDIdNumber(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.BVD_ID_NUMBER;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyISOCountryCode_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setISOCountryCode(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.ISO_COUNTRY_CODE;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyNaceMainSection_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setNaceRevMainSection(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.NACE_REV_MAIN_SECTION;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyNaceCoreCode_shouldReturnCorrectGetter() {
        int testValue = 123;
        companyExportDto.setNaceRevCoreCode(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.NACE_REV_CORE_CODE;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyConsolidationCode_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setConsolidationCode(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.CONSOLIDATION_CODE;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyOperatingIncome_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setOperatingIncome(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.OPERATING_INCOME;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyEmployeesCount_shouldReturnCorrectGetter() {
        int testValue = 100;
        companyExportDto.setEmployeesCount(100);
        CompaniesExportColumn testColumn = CompaniesExportColumn.EMPLOYEES_COUNT;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyAddress_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setAddress(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.ADDRESS;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyJobDescription_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setJobDescription(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.JOB_DESCRIPTION;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyStandardizedLegalForm_shouldReturnCorrectGetter() {
        String testValue = "test value";
        companyExportDto.setStandardizedLegalForm(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.STANDARDIZED_LEGAL_FORM;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyManagersCount_shouldReturnCorrectGetter() {
        int testValue = 10;
        companyExportDto.setManagersCount(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.MANAGERS_COUNT;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companyCorporationCompaniesCount_shouldReturnCorrectGetter() {
        int testValue = 10;
        companyExportDto.setCorporationCompaniesCount(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.CORPORATION_COMPANIES_COUNT;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }

    @Test
    public void getProperty_companySubsidiariesCount_shouldReturnCorrectGetter() {
        int testValue = 10;
        companyExportDto.setSubsidiariesCount(testValue);
        CompaniesExportColumn testColumn = CompaniesExportColumn.SUBSIDIARIES_COUNT;

        Object property = companyExportDto.getProperty(testColumn);

        Assert.assertEquals(testValue, property);
    }
}
