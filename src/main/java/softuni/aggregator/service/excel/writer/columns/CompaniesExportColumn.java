package softuni.aggregator.service.excel.writer.columns;

import softuni.aggregator.service.excel.writer.model.CompaniesExportExcelDto;

import java.util.function.Function;

public enum CompaniesExportColumn implements WriteExcelColumn<CompaniesExportExcelDto> {

    COMPANY_NAME("Company name", CompaniesExportExcelDto::getName),
    WEBSITE("Website", CompaniesExportExcelDto::getWebsite),
    VAT_NUMBER("VAT/Steuernummer", CompaniesExportExcelDto::getVATNumber),
    BVD_ID_NUMBER("BvD ID Nummer", CompaniesExportExcelDto::getBvDIdNumber),
    ISO_COUNTRY_CODE("ISO Ländercode", CompaniesExportExcelDto::getISOCountryCode),
    NACE_REV_MAIN_SECTION("NACE Rev. 2 Hauptsektion", CompaniesExportExcelDto::getNaceRevMainSection),
    NACE_REV_CORE_CODE("NACE Rev. 2 Core Code (4 Ziffern)", CompaniesExportExcelDto::getNaceRevCoreCode),
    CONSOLIDATION_CODE("Konsolidierungscode", CompaniesExportExcelDto::getConsolidationCode),
    OPERATING_INCOME("Betriebsertrag (Umsatz)\ntsd EUR Letztes verf. Jahr", CompaniesExportExcelDto::getOperatingIncome),
    EMPLOYEES_COUNT("Anzahl der Mitarbeiter\nLetztes verf. Jahr", CompaniesExportExcelDto::getEmployeesCount),
    ADDRESS("Straße, Hausnr., Gebäude, etc., Zeile 1", CompaniesExportExcelDto::getAddress),
    POSTCODE("Postleitzahl", CompaniesExportExcelDto::getPostcode),
    LOCATION("Ort", CompaniesExportExcelDto::getCity),
    COUNTRY("Land", CompaniesExportExcelDto::getCountry),
    PHONE_NUMBER("Telefon", CompaniesExportExcelDto::getCompanyPhone),
    COMPANY_EMAIL("E-Mail Adresse", CompaniesExportExcelDto::getCompanyEmails),
    JOB_DESCRIPTION("Tätigkeitsbeschreibung (Englisch)", CompaniesExportExcelDto::getJobDescription),
    STANDARDIZED_LEGAL_FORM("Standardisierte Rechtsform", CompaniesExportExcelDto::getStandardizedLegalForm),
    MANAGERS_COUNT("Anzahl der Geschäftsführer & Manager", CompaniesExportExcelDto::getManagersCount),
    CORPORATION_COMPANIES_COUNT("Anzahl Unternehmen in der Konzerngruppe", CompaniesExportExcelDto::getCorporationCompaniesCount),
    EMPLOYEES_COUNT_SKIP("Anzahl der Mitarbeiter\nLetztes verf. Jahr.1", CompaniesExportExcelDto::getEmployeesCount),
    SUBSIDIARIES_COUNT("Anzahl der dokumentierten Tochtergesellschaften", CompaniesExportExcelDto::getSubsidiariesCount),

    XING_INDUSTRY_1("Xing industry 1", CompaniesExportExcelDto::getXingIndustry1),
    XING_INDUSTRY_2("Xing industry 2", CompaniesExportExcelDto::getXingIndustry2),
    EMPLOYEES_RANGE("Employee range", CompaniesExportExcelDto::getEmployeesRange),
    STREET("Street", CompaniesExportExcelDto::getStreet),
    COMPANY_PHONE("Company phone", CompaniesExportExcelDto::getCompanyPhone),
    FAX("Fax", CompaniesExportExcelDto::getFax),
    INFORMATION("Information", CompaniesExportExcelDto::getInformation),
    EMPLOYEES_LISTED("Employees listed", CompaniesExportExcelDto::getEmployeesListed),
    EMPLOYEES_PAGE("Employees page", CompaniesExportExcelDto::getEmployeesPage),
    COMPANY_PROFILE_LINK("Company profile link", CompaniesExportExcelDto::getCompanyProfileLink),
    YEAR_FOUND("Year found", CompaniesExportExcelDto::getYearFound),
    PRODUCTS_AND_SERVICES("Products and services", CompaniesExportExcelDto::getProductsAndServices);

    private String columnName;
    private Function<CompaniesExportExcelDto, ?> getter;

    CompaniesExportColumn(String columnName, Function<CompaniesExportExcelDto, ?> getter) {
        this.columnName = columnName;
        this.getter = getter;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public Function<CompaniesExportExcelDto, ?> getGetter() {
        return getter;
    }
}
