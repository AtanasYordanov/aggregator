package softuni.aggregator.service.excel.writer.columns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import softuni.aggregator.service.excel.writer.model.CompanyExportDto;

import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum CompaniesExportColumn implements WriteExcelColumn<CompanyExportDto> {

    COMPANY_NAME("Company name", CompanyExportDto::getName),
    XING_INDUSTRY_1("Xing industry 1", CompanyExportDto::getXingIndustry1),
    XING_INDUSTRY_2("Xing industry 2", CompanyExportDto::getXingIndustry2),
    WEBSITE("Website", CompanyExportDto::getWebsite),
    COMPANY_PHONE("Company phone", CompanyExportDto::getCompanyPhone),
    COMPANY_EMAIL("E-Mail Address", CompanyExportDto::getCompanyEmails),
    FAX("Fax", CompanyExportDto::getFax),
    ADDRESS("Address", CompanyExportDto::getAddress),
    STREET("Street", CompanyExportDto::getStreet),
    COUNTRY("Country", CompanyExportDto::getCountry),
    CITY("City", CompanyExportDto::getCity),
    VAT_NUMBER("VAT Number", CompanyExportDto::getVATNumber),
    BVD_ID_NUMBER("BvD ID Number", CompanyExportDto::getBvDIdNumber),
    ISO_COUNTRY_CODE("ISO Country code", CompanyExportDto::getISOCountryCode),
    YEAR_FOUND("Year found", CompanyExportDto::getYearFound),
    NACE_REV_MAIN_SECTION("NACE Rev. 2 Main section", CompanyExportDto::getNaceRevMainSection),
    NACE_REV_CORE_CODE("NACE Rev. 2 Core Code", CompanyExportDto::getNaceRevCoreCode),
    CONSOLIDATION_CODE("Consolidation code", CompanyExportDto::getConsolidationCode),
    OPERATING_INCOME("Operating income", CompanyExportDto::getOperatingIncome),
    POSTCODE("Postcode", CompanyExportDto::getPostcode),
    EMPLOYEES_COUNT("Employees count", CompanyExportDto::getEmployeesCount),
    EMPLOYEES_RANGE("Employee range", CompanyExportDto::getEmployeesRange),
    EMPLOYEES_PAGE("Employees page", CompanyExportDto::getEmployeesPage),
    CORPORATION_COMPANIES_COUNT("Corporation companies count", CompanyExportDto::getCorporationCompaniesCount),
    MANAGERS_COUNT("Managers count", CompanyExportDto::getManagersCount),
    SUBSIDIARIES_COUNT("Subsidiaries count", CompanyExportDto::getSubsidiariesCount),
    STANDARDIZED_LEGAL_FORM("Standardized legal form", CompanyExportDto::getStandardizedLegalForm),
    JOB_DESCRIPTION("Job description", CompanyExportDto::getJobDescription),
    INFORMATION("Information", CompanyExportDto::getInformation),
    COMPANY_PROFILE_LINK("Xing profile", CompanyExportDto::getXingProfileLink),
    PRODUCTS_AND_SERVICES("Products and services", CompanyExportDto::getProductsAndServices);

    private String columnName;
    private Function<CompanyExportDto, ?> getter;
}
