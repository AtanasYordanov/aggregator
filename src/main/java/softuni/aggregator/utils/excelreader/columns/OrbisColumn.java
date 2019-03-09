package softuni.aggregator.utils.excelreader.columns;

import softuni.aggregator.utils.excelreader.model.OrbisCompanyDto;

import java.util.function.BiConsumer;

public enum OrbisColumn implements ExcelColumn<OrbisCompanyDto> {

    COMPANY_NAME(OrbisCompanyDto::setName),
    WEBSITE(OrbisCompanyDto::setWebsite),
    VAT_NUMBER(OrbisCompanyDto::setVATNumber),
    BVD_ID_NUMBER(OrbisCompanyDto::setBVDIdNumber),
    ISO_COUNTRY_CODE(OrbisCompanyDto::setISOCountryCode),
    NACE_REV_MAIN_SECTION(OrbisCompanyDto::setNaceRevMainSection),
    NACE_REV_CORE_CODE(OrbisCompanyDto::setNaceRevCoreCode),
    CONSOLIDATION_CODE(OrbisCompanyDto::setConsolidationCode),
    OPERATING_INCOME(OrbisCompanyDto::setOperatingIncome),
    EMPLOYEES_COUNT(OrbisCompanyDto::setEmployeesCount),
    ADDRESS(OrbisCompanyDto::setAddress),
    POSTCODE(OrbisCompanyDto::setPostcode),
    LOCATION(OrbisCompanyDto::setLocation),
    COUNTRY(OrbisCompanyDto::setCountry),
    PHONE_NUMBER(OrbisCompanyDto::setPhoneNumber),
    COMPANY_EMAIL(OrbisCompanyDto::setCompanyEmail),
    JOB_DESCRIPTION(OrbisCompanyDto::setJobDescription),
    STANDARDIZED_LEGAL_FORM(OrbisCompanyDto::setStandardizedLegalForm),
    MANAGERS_COUNT(OrbisCompanyDto::setManagersCount),
    CORPORATION_COMPANIES_COUNT(OrbisCompanyDto::setCorporationCompaniesCount),
    EMPLOYEES_COUNT_SKIP(OrbisCompanyDto::setEmployeesCount),
    SUBSIDIARIES_COUNT(OrbisCompanyDto::setSubsidiariesCount);

    OrbisColumn(BiConsumer<OrbisCompanyDto, String> setter) {
        this.setter = setter;
    }

    private BiConsumer<OrbisCompanyDto, String> setter;

    @Override
    public BiConsumer<OrbisCompanyDto, String> getSetter() {
        return setter;
    }
}
