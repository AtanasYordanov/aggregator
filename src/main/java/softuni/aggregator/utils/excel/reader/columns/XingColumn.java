package softuni.aggregator.utils.excel.reader.columns;

import softuni.aggregator.utils.excel.reader.model.XingCompanyDto;

import java.util.function.BiConsumer;

public enum XingColumn implements ReadExcelColumn<XingCompanyDto> {

    XING_INDUSTRY_1(XingCompanyDto::setXingIndustry1),
    XING_INDUSTRY_2(XingCompanyDto::setXingIndustry2),
    COMPANY_NAME(XingCompanyDto::setName),
    WEBSITE(XingCompanyDto::setWebsite),
    EMPLOYEES_RANGE(XingCompanyDto::setEmployeesRange),
    STREET(XingCompanyDto::setStreet),
    POSTCODE(XingCompanyDto::setPostcode),
    CITY(XingCompanyDto::setCity),
    COUNTRY(XingCompanyDto::setCountry),
    COMPANY_PHONE(XingCompanyDto::setCompanyPhone),
    FAX(XingCompanyDto::setFax),
    COMPANY_EMAIL(XingCompanyDto::setCompanyEmail),
    INFORMATION(XingCompanyDto::setInformation),
    EMPLOYEES_LISTED(XingCompanyDto::setEmployeesListed),
    EMPLOYEES_PAGE(XingCompanyDto::setEmployeesPage),
    COMPANY_PROFILE_LINK(XingCompanyDto::setCompanyProfileLink),
    YEAR_FOUND(XingCompanyDto::setYearFound);

    private BiConsumer<XingCompanyDto, String> setter;

    XingColumn(BiConsumer<XingCompanyDto, String> setter) {
        this.setter = setter;
    }

    @Override
    public BiConsumer<XingCompanyDto, String> getSetter() {
        return setter;
    }
}
