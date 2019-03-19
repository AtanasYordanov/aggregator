package softuni.aggregator.service.excel.reader.columns;

import softuni.aggregator.service.excel.reader.model.OrbisCompanyDto;

import java.util.function.BiConsumer;

public enum OrbisImportColumn implements ReadExcelColumn<OrbisCompanyDto> {

    COMPANY_NAME("Company name", OrbisCompanyDto::setName),
    WEBSITE("Website", OrbisCompanyDto::setWebsite),
    VAT_NUMBER("VAT/Steuernummer", OrbisCompanyDto::setVATNumber),
    BVD_ID_NUMBER("BvD ID Nummer", OrbisCompanyDto::setBvDIdNumber),
    ISO_COUNTRY_CODE("ISO Ländercode", OrbisCompanyDto::setISOCountryCode),
    NACE_REV_MAIN_SECTION("NACE Rev. 2 Hauptsektion", OrbisCompanyDto::setNaceRevMainSection),
    NACE_REV_CORE_CODE("NACE Rev. 2 Core Code (4 Ziffern)", OrbisCompanyDto::setNaceRevCoreCode),
    CONSOLIDATION_CODE("Konsolidierungscode", OrbisCompanyDto::setConsolidationCode),
    OPERATING_INCOME("Betriebsertrag (Umsatz)\ntsd EUR Letztes verf. Jahr", OrbisCompanyDto::setOperatingIncome),
    EMPLOYEES_COUNT("Anzahl der Mitarbeiter\nLetztes verf. Jahr", OrbisCompanyDto::setEmployeesCount),
    ADDRESS("Straße, Hausnr., Gebäude, etc., Zeile 1", OrbisCompanyDto::setAddress),
    POSTCODE("Postleitzahl", OrbisCompanyDto::setPostcode),
    LOCATION("Ort", OrbisCompanyDto::setCity),
    COUNTRY("Land", OrbisCompanyDto::setCountry),
    PHONE_NUMBER("Telefon", OrbisCompanyDto::setCompanyPhone),
    COMPANY_EMAIL("E-Mail Adresse", OrbisCompanyDto::setCompanyEmail),
    JOB_DESCRIPTION("Tätigkeitsbeschreibung (Englisch)", OrbisCompanyDto::setJobDescription),
    STANDARDIZED_LEGAL_FORM("Standardisierte Rechtsform", OrbisCompanyDto::setStandardizedLegalForm),
    MANAGERS_COUNT("Anzahl der Geschäftsführer & Manager", OrbisCompanyDto::setManagersCount),
    CORPORATION_COMPANIES_COUNT("Anzahl Unternehmen in der Konzerngruppe", OrbisCompanyDto::setCorporationCompaniesCount),
    EMPLOYEES_COUNT_SKIP("Anzahl der Mitarbeiter\nLetztes verf. Jahr.1", OrbisCompanyDto::setEmployeesCount),
    SUBSIDIARIES_COUNT("Anzahl der dokumentierten Tochtergesellschaften", OrbisCompanyDto::setSubsidiariesCount);

    private String columnName;
    private BiConsumer<OrbisCompanyDto, String> setter;

    OrbisImportColumn(String columnName, BiConsumer<OrbisCompanyDto, String> setter) {
        this.columnName = columnName;
        this.setter = setter;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
    
    @Override
    public BiConsumer<OrbisCompanyDto, String> getSetter() {
        return setter;
    }
}
