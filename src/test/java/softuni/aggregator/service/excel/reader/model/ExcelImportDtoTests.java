package softuni.aggregator.service.excel.reader.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import softuni.aggregator.service.excel.reader.columns.EmployeesImportColumn;
import softuni.aggregator.service.excel.reader.columns.OrbisImportColumn;
import softuni.aggregator.service.excel.reader.columns.XingImportColumn;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ExcelImportDtoTests {

    private EmployeeImportDto employeeImportDto;
    private XingCompanyImportDto xingCompanyImportDto;
    private OrbisCompanyImportDto orbisCompanyImportDto;

    @Before
    public void init() {
        employeeImportDto = new EmployeeImportDto();
        xingCompanyImportDto = new XingCompanyImportDto();
        orbisCompanyImportDto = new OrbisCompanyImportDto();
    }

    @Test
    public void setProperty_employeeCompanyName_shouldReturnCorrectSetter() {
        String testValue = "test company";
        EmployeesImportColumn testColumn = EmployeesImportColumn.COMPANY_NAME;

        employeeImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, employeeImportDto.getCompanyName());
    }

    @Test
    public void setProperty_employeeFullName_shouldReturnCorrectSetter() {
        String testValue = "test name";
        EmployeesImportColumn testColumn = EmployeesImportColumn.FULL_NAME;

        employeeImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, employeeImportDto.getFullName());
    }

    @Test
    public void setProperty_employeeCompanyWebsite_shouldReturnCorrectSetter() {
        String testValue = "test website";
        EmployeesImportColumn testColumn = EmployeesImportColumn.COMPANY_WEBSITE;

        employeeImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, employeeImportDto.getCompanyWebsite());
    }

    @Test
    public void setProperty_employeeEmail_shouldReturnCorrectSetter() {
        String testValue = "test email";
        EmployeesImportColumn testColumn = EmployeesImportColumn.EMAIL;

        employeeImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, employeeImportDto.getEmail());
    }

    @Test
    public void setProperty_employeeHunterIoScore_shouldReturnCorrectSetter() {
        String testValue = "test score";
        EmployeesImportColumn testColumn = EmployeesImportColumn.HUNTER_IO_SCORE;

        employeeImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, employeeImportDto.getHunterIoScore());
    }

    @Test
    public void setProperty_employeePosition_shouldReturnCorrectSetter() {
        String testValue = "test position";
        EmployeesImportColumn testColumn = EmployeesImportColumn.POSITION;

        employeeImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, employeeImportDto.getPosition());
    }

    @Test
    public void setProperty_xingCompanyName_shouldReturnCorrectSetter() {
        String testValue = "test company name";
        XingImportColumn testColumn = XingImportColumn.COMPANY_NAME;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getName());
    }

    @Test
    public void setProperty_xingCity_shouldReturnCorrectSetter() {
        String testValue = "test city";
        XingImportColumn testColumn = XingImportColumn.CITY;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getCity());
    }

    @Test
    public void setProperty_xingCountry_shouldReturnCorrectSetter() {
        String testValue = "test country";
        XingImportColumn testColumn = XingImportColumn.COUNTRY;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getCountry());
    }

    @Test
    public void setProperty_xingCompanyEmail_shouldReturnCorrectSetter() {
        String testValue = "test company email";
        XingImportColumn testColumn = XingImportColumn.COMPANY_EMAIL;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getCompanyEmail());
    }

    @Test
    public void setProperty_xingCompanyPhone_shouldReturnCorrectSetter() {
        String testValue = "test company phone";
        XingImportColumn testColumn = XingImportColumn.COMPANY_PHONE;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getCompanyPhone());
    }

    @Test
    public void setProperty_xingWebsite_shouldReturnCorrectSetter() {
        String testValue = "test website";
        XingImportColumn testColumn = XingImportColumn.WEBSITE;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getWebsite());
    }

    @Test
    public void setProperty_xingPostcode_shouldReturnCorrectSetter() {
        String testValue = "test postcode";
        XingImportColumn testColumn = XingImportColumn.POSTCODE;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getPostcode());
    }

    @Test
    public void setProperty_xingIndustry1_shouldReturnCorrectSetter() {
        String testValue = "test industry";
        XingImportColumn testColumn = XingImportColumn.XING_INDUSTRY_1;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getXingIndustry1());
    }

    @Test
    public void setProperty_employeesRange_shouldReturnCorrectSetter() {
        String testValue = "test range";
        XingImportColumn testColumn = XingImportColumn.EMPLOYEES_RANGE;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getEmployeesRange());
    }

    @Test
    public void setProperty_xingStreet_shouldReturnCorrectSetter() {
        String testValue = "test industry";
        XingImportColumn testColumn = XingImportColumn.STREET;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getStreet());
    }

    @Test
    public void setProperty_xingFax_shouldReturnCorrectSetter() {
        String testValue = "test fax";
        XingImportColumn testColumn = XingImportColumn.FAX;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getFax());
    }

    @Test
    public void setProperty_xingInformation_shouldReturnCorrectSetter() {
        String testValue = "test information";
        XingImportColumn testColumn = XingImportColumn.INFORMATION;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getInformation());
    }

    @Test
    public void setProperty_xingEmployeesListed_shouldReturnCorrectSetter() {
        String testValue = "test employees listed";
        XingImportColumn testColumn = XingImportColumn.EMPLOYEES_LISTED;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getEmployeesListed());
    }

    @Test
    public void setProperty_xingEmployeesPage_shouldReturnCorrectSetter() {
        String testValue = "test employees page";
        XingImportColumn testColumn = XingImportColumn.EMPLOYEES_PAGE;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getEmployeesPage());
    }

    @Test
    public void setProperty_xingProfileLink_shouldReturnCorrectSetter() {
        String testValue = "test profile link";
        XingImportColumn testColumn = XingImportColumn.COMPANY_PROFILE_LINK;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getXingProfileLink());
    }

    @Test
    public void setProperty_xingYearFound_shouldReturnCorrectSetter() {
        String testValue = "test profile link";
        XingImportColumn testColumn = XingImportColumn.YEAR_FOUND;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getYearFound());
    }

    @Test
    public void setProperty_xingProductsAndServices_shouldReturnCorrectSetter() {
        String testValue = "test products and services";
        XingImportColumn testColumn = XingImportColumn.PRODUCTS_AND_SERVICES;

        xingCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, xingCompanyImportDto.getProductsAndServices());
    }

    @Test
    public void setProperty_orbisBvDNumber_shouldReturnCorrectSetter() {
        String testValue = "test bvd";
        OrbisImportColumn testColumn = OrbisImportColumn.BVD_ID_NUMBER;

        orbisCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, orbisCompanyImportDto.getBvDIdNumber());
    }

    @Test
    public void setProperty_orbisISOCountryCode_shouldReturnCorrectSetter() {
        String testValue = "test iso";
        OrbisImportColumn testColumn = OrbisImportColumn.ISO_COUNTRY_CODE;

        orbisCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, orbisCompanyImportDto.getISOCountryCode());
    }

    @Test
    public void setProperty_orbisNaceMainSection_shouldReturnCorrectSetter() {
        String testValue = "test nms";
        OrbisImportColumn testColumn = OrbisImportColumn.NACE_REV_MAIN_SECTION;

        orbisCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, orbisCompanyImportDto.getNaceRevMainSection());
    }

    @Test
    public void setProperty_orbisNaceCoreCore_shouldReturnCorrectSetter() {
        String testValue = "test ncc";
        OrbisImportColumn testColumn = OrbisImportColumn.NACE_REV_CORE_CODE;

        orbisCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, orbisCompanyImportDto.getNaceRevCoreCode());
    }

    @Test
    public void setProperty_orbisConsolidationCode_shouldReturnCorrectSetter() {
        String testValue = "test cc";
        OrbisImportColumn testColumn = OrbisImportColumn.CONSOLIDATION_CODE;

        orbisCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, orbisCompanyImportDto.getConsolidationCode());
    }

    @Test
    public void setProperty_orbisOperatingIncome_shouldReturnCorrectSetter() {
        String testValue = "test income";
        OrbisImportColumn testColumn = OrbisImportColumn.OPERATING_INCOME;

        orbisCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, orbisCompanyImportDto.getOperatingIncome());
    }

    @Test
    public void setProperty_orbisEmployeesCount_shouldReturnCorrectSetter() {
        String testValue = "test employees count";
        OrbisImportColumn testColumn = OrbisImportColumn.EMPLOYEES_COUNT;

        orbisCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, orbisCompanyImportDto.getEmployeesCount());
    }

    @Test
    public void setProperty_orbisAddress_shouldReturnCorrectSetter() {
        String testValue = "test address";
        OrbisImportColumn testColumn = OrbisImportColumn.ADDRESS;

        orbisCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, orbisCompanyImportDto.getAddress());
    }

    @Test
    public void setProperty_orbisJobDescription_shouldReturnCorrectSetter() {
        String testValue = "test job description";
        OrbisImportColumn testColumn = OrbisImportColumn.JOB_DESCRIPTION;

        orbisCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, orbisCompanyImportDto.getJobDescription());
    }

    @Test
    public void setProperty_orbisStandardizedLegalForm_shouldReturnCorrectSetter() {
        String testValue = "test legam form";
        OrbisImportColumn testColumn = OrbisImportColumn.STANDARDIZED_LEGAL_FORM;

        orbisCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, orbisCompanyImportDto.getStandardizedLegalForm());
    }

    @Test
    public void setProperty_orbisManagersCount_shouldReturnCorrectSetter() {
        String testValue = "test managers count";
        OrbisImportColumn testColumn = OrbisImportColumn.MANAGERS_COUNT;

        orbisCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, orbisCompanyImportDto.getManagersCount());
    }

    @Test
    public void setProperty_orbisCorporationCompaniesCount_shouldReturnCorrectSetter() {
        String testValue = "test corp com count";
        OrbisImportColumn testColumn = OrbisImportColumn.CORPORATION_COMPANIES_COUNT;

        orbisCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, orbisCompanyImportDto.getCorporationCompaniesCount());
    }

    @Test
    public void setProperty_orbisSubsidiariesCount_shouldReturnCorrectSetter() {
        String testValue = "test subs count";
        OrbisImportColumn testColumn = OrbisImportColumn.SUBSIDIARIES_COUNT;

        orbisCompanyImportDto.setProperty(testColumn, testValue);

        Assert.assertEquals(testValue, orbisCompanyImportDto.getSubsidiariesCount());
    }
}
