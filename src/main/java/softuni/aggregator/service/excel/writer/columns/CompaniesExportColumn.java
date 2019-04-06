package softuni.aggregator.service.excel.writer.columns;

import lombok.AllArgsConstructor;
import softuni.aggregator.service.excel.writer.model.CompaniesExportDto;

import java.util.function.Function;

@AllArgsConstructor
public enum CompaniesExportColumn implements WriteExcelColumn<CompaniesExportDto> {

    COMPANY_NAME("Company name", CompaniesExportDto::getName),
    WEBSITE("Website", CompaniesExportDto::getWebsite),
    VAT_NUMBER("VAT/Steuernummer", CompaniesExportDto::getVATNumber),
    BVD_ID_NUMBER("BvD ID Nummer", CompaniesExportDto::getBvDIdNumber),
    ISO_COUNTRY_CODE("ISO Ländercode", CompaniesExportDto::getISOCountryCode),
    NACE_REV_MAIN_SECTION("NACE Rev. 2 Hauptsektion", CompaniesExportDto::getNaceRevMainSection),
    NACE_REV_CORE_CODE("NACE Rev. 2 Core Code (4 Ziffern)", CompaniesExportDto::getNaceRevCoreCode),
    CONSOLIDATION_CODE("Konsolidierungscode", CompaniesExportDto::getConsolidationCode),
    OPERATING_INCOME("Betriebsertrag (Umsatz)\ntsd EUR Letztes verf. Jahr", CompaniesExportDto::getOperatingIncome),
    EMPLOYEES_COUNT("Anzahl der Mitarbeiter\nLetztes verf. Jahr", CompaniesExportDto::getEmployeesCount),
    ADDRESS("Straße, Hausnr., Gebäude, etc., Zeile 1", CompaniesExportDto::getAddress),
    POSTCODE("Postleitzahl", CompaniesExportDto::getPostcode),
    LOCATION("Ort", CompaniesExportDto::getCity),
    COUNTRY("Land", CompaniesExportDto::getCountry),
    PHONE_NUMBER("Telefon", CompaniesExportDto::getCompanyPhone),
    COMPANY_EMAIL("E-Mail Adresse", CompaniesExportDto::getCompanyEmails),
    JOB_DESCRIPTION("Tätigkeitsbeschreibung (Englisch)", CompaniesExportDto::getJobDescription),
    STANDARDIZED_LEGAL_FORM("Standardisierte Rechtsform", CompaniesExportDto::getStandardizedLegalForm),
    MANAGERS_COUNT("Anzahl der Geschäftsführer & Manager", CompaniesExportDto::getManagersCount),
    CORPORATION_COMPANIES_COUNT("Anzahl Unternehmen in der Konzerngruppe", CompaniesExportDto::getCorporationCompaniesCount),
    EMPLOYEES_COUNT_SKIP("Anzahl der Mitarbeiter\nLetztes verf. Jahr.1", CompaniesExportDto::getEmployeesCount),
    SUBSIDIARIES_COUNT("Anzahl der dokumentierten Tochtergesellschaften", CompaniesExportDto::getSubsidiariesCount),

    XING_INDUSTRY_1("Xing industry 1", CompaniesExportDto::getXingIndustry1),
    XING_INDUSTRY_2("Xing industry 2", CompaniesExportDto::getXingIndustry2),
    EMPLOYEES_RANGE("Employee range", CompaniesExportDto::getEmployeesRange),
    STREET("Street", CompaniesExportDto::getStreet),
    COMPANY_PHONE("Company phone", CompaniesExportDto::getCompanyPhone),
    FAX("Fax", CompaniesExportDto::getFax),
    INFORMATION("Information", CompaniesExportDto::getInformation),
    EMPLOYEES_LISTED("Employees listed", CompaniesExportDto::getEmployeesListed),
    EMPLOYEES_PAGE("Employees page", CompaniesExportDto::getEmployeesPage),
    COMPANY_PROFILE_LINK("Xing profile", CompaniesExportDto::getXingProfileLink),
    YEAR_FOUND("Year found", CompaniesExportDto::getYearFound),
    PRODUCTS_AND_SERVICES("Products and services", CompaniesExportDto::getProductsAndServices);

    private String columnName;
    private Function<CompaniesExportDto, ?> getter;

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public Function<CompaniesExportDto, ?> getGetter() {
        return getter;
    }
}
