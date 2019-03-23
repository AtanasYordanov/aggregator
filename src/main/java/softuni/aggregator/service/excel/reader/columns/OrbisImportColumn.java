package softuni.aggregator.service.excel.reader.columns;

import softuni.aggregator.service.excel.reader.model.OrbisCompanyImportDto;

import java.util.function.BiConsumer;

public enum OrbisImportColumn implements ReadExcelColumn<OrbisCompanyImportDto> {

    COMPANY_NAME("Company name", OrbisCompanyImportDto::setName),
    WEBSITE("Website", OrbisCompanyImportDto::setWebsite),
    VAT_NUMBER("VAT/Steuernummer", OrbisCompanyImportDto::setVATNumber),
    BVD_ID_NUMBER("BvD ID Nummer", OrbisCompanyImportDto::setBvDIdNumber),
    ISO_COUNTRY_CODE("ISO Ländercode", OrbisCompanyImportDto::setISOCountryCode),
    NACE_REV_MAIN_SECTION("NACE Rev. 2 Hauptsektion", OrbisCompanyImportDto::setNaceRevMainSection),
    NACE_REV_CORE_CODE("NACE Rev. 2 Core Code (4 Ziffern)", OrbisCompanyImportDto::setNaceRevCoreCode),
    CONSOLIDATION_CODE("Konsolidierungscode", OrbisCompanyImportDto::setConsolidationCode),
    OPERATING_INCOME("Betriebsertrag (Umsatz)\ntsd EUR Letztes verf. Jahr", OrbisCompanyImportDto::setOperatingIncome),
    EMPLOYEES_COUNT("Anzahl der Mitarbeiter\nLetztes verf. Jahr", OrbisCompanyImportDto::setEmployeesCount),
    ADDRESS("Straße, Hausnr., Gebäude, etc., Zeile 1", OrbisCompanyImportDto::setAddress),
    POSTCODE("Postleitzahl", OrbisCompanyImportDto::setPostcode),
    LOCATION("Ort", OrbisCompanyImportDto::setCity),
    COUNTRY("Land", OrbisCompanyImportDto::setCountry),
    PHONE_NUMBER("Telefon", OrbisCompanyImportDto::setCompanyPhone),
    COMPANY_EMAIL("E-Mail Adresse", OrbisCompanyImportDto::setCompanyEmail),
    JOB_DESCRIPTION("Tätigkeitsbeschreibung (Englisch)", OrbisCompanyImportDto::setJobDescription),
    STANDARDIZED_LEGAL_FORM("Standardisierte Rechtsform", OrbisCompanyImportDto::setStandardizedLegalForm),
    MANAGERS_COUNT("Anzahl der Geschäftsführer & Manager", OrbisCompanyImportDto::setManagersCount),
    CORPORATION_COMPANIES_COUNT("Anzahl Unternehmen in der Konzerngruppe", OrbisCompanyImportDto::setCorporationCompaniesCount),
    EMPLOYEES_COUNT_SKIP("Anzahl der Mitarbeiter\nLetztes verf. Jahr.1", OrbisCompanyImportDto::setEmployeesCount),
    SUBSIDIARIES_COUNT("Anzahl der dokumentierten Tochtergesellschaften", OrbisCompanyImportDto::setSubsidiariesCount);

    private String columnName;
    private BiConsumer<OrbisCompanyImportDto, String> setter;

    OrbisImportColumn(String columnName, BiConsumer<OrbisCompanyImportDto, String> setter) {
        this.columnName = columnName;
        this.setter = setter;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public BiConsumer<OrbisCompanyImportDto, String> getSetter() {
        return setter;
    }
}
