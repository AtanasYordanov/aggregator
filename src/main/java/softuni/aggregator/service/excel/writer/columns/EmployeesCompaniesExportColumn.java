package softuni.aggregator.service.excel.writer.columns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import softuni.aggregator.service.excel.writer.model.EmployeeWithCompanyExportDto;

import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum EmployeesCompaniesExportColumn implements WriteExcelColumn<EmployeeWithCompanyExportDto> {

    FULL_NAME("Full name", (d) -> d.getEmployeeExportDto().getFullName()),
    POSITION("Position", (d) -> d.getEmployeeExportDto().getPosition()),
    EMAIL("Email", (d) -> d.getEmployeeExportDto().getEmail()),
    HUNTER_IO_SCORE("Hunter.io Score", (d) -> d.getEmployeeExportDto().getHunterIoScore()),

    COMPANY_NAME("Company name", (d) -> d.getCompanyExportDto().getName()),
    XING_INDUSTRY_1("Xing industry 1", (d) -> d.getCompanyExportDto().getXingIndustry1()),
    XING_INDUSTRY_2("Xing industry 2", (d) -> d.getCompanyExportDto().getXingIndustry2()),
    WEBSITE("Website", (d) -> d.getCompanyExportDto().getWebsite()),
    COMPANY_PHONE("Company phone", (d) -> d.getCompanyExportDto().getCompanyPhone()),
    COMPANY_EMAIL("E-Mail Address", (d) -> d.getCompanyExportDto().getCompanyEmails()),
    FAX("Fax", (d) -> d.getCompanyExportDto().getFax()),
    ADDRESS("Address", (d) -> d.getCompanyExportDto().getAddress()),
    STREET("Street", (d) -> d.getCompanyExportDto().getStreet()),
    COUNTRY("Country", (d) -> d.getCompanyExportDto().getCountry()),
    CITY("City", (d) -> d.getCompanyExportDto().getCity()),
    VAT_NUMBER("VAT Number", (d) -> d.getCompanyExportDto().getVATNumber()),
    BVD_ID_NUMBER("BvD ID Number", (d) -> d.getCompanyExportDto().getBvDIdNumber()),
    ISO_COUNTRY_CODE("ISO Country code", (d) -> d.getCompanyExportDto().getISOCountryCode()),
    YEAR_FOUND("Year found", (d) -> d.getCompanyExportDto().getYearFound()),
    NACE_REV_MAIN_SECTION("NACE Rev. 2 Main section", (d) -> d.getCompanyExportDto().getNaceRevMainSection()),
    NACE_REV_CORE_CODE("NACE Rev. 2 Core Code", (d) -> d.getCompanyExportDto().getNaceRevCoreCode()),
    CONSOLIDATION_CODE("Consolidation code", (d) -> d.getCompanyExportDto().getConsolidationCode()),
    OPERATING_INCOME("Operating income", (d) -> d.getCompanyExportDto().getOperatingIncome()),
    POSTCODE("Postcode", (d) -> d.getCompanyExportDto().getPostcode()),
    EMPLOYEES_COUNT("Employees count", (d) -> d.getCompanyExportDto().getEmployeesCount()),
    EMPLOYEES_RANGE("Employee range", (d) -> d.getCompanyExportDto().getEmployeesRange()),
    EMPLOYEES_PAGE("Employees page", (d) -> d.getCompanyExportDto().getEmployeesPage()),
    CORPORATION_COMPANIES_COUNT("Corporation companies count", (d) -> d.getCompanyExportDto().getCorporationCompaniesCount()),
    MANAGERS_COUNT("Managers count", (d) -> d.getCompanyExportDto().getManagersCount()),
    SUBSIDIARIES_COUNT("Subsidiaries count", (d) -> d.getCompanyExportDto().getSubsidiariesCount()),
    STANDARDIZED_LEGAL_FORM("Standardized legal form", (d) -> d.getCompanyExportDto().getStandardizedLegalForm()),
    JOB_DESCRIPTION("Job description",(d) -> d.getCompanyExportDto().getJobDescription()),
    INFORMATION("Information", (d) -> d.getCompanyExportDto().getInformation()),
    COMPANY_PROFILE_LINK("Xing profile", (d) -> d.getCompanyExportDto().getXingProfileLink()),
    PRODUCTS_AND_SERVICES("Products and services", (d) -> d.getCompanyExportDto().getProductsAndServices());

    private String columnName;
    private Function<EmployeeWithCompanyExportDto, ?> getter;
}
