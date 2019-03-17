package softuni.aggregator.utils.excel.reader.columns;

import softuni.aggregator.utils.excel.reader.model.XingCompanyDto;

import java.util.function.BiConsumer;

public enum XingColumn implements ReadExcelColumn<XingCompanyDto> {

    XING_INDUSTRY_1("Xing industry 1", XingCompanyDto::setXingIndustry1),
    XING_INDUSTRY_2("Xing industry 2", XingCompanyDto::setXingIndustry2),
    COMPANY_NAME("Company name", XingCompanyDto::setName),
    WEBSITE("Website", XingCompanyDto::setWebsite),
    EMPLOYEES_RANGE("Employee range", XingCompanyDto::setEmployeesRange),
    STREET("Street", XingCompanyDto::setStreet),
    POSTCODE("Postcode", XingCompanyDto::setPostcode),
    CITY("City", XingCompanyDto::setCity),
    COUNTRY("Country", XingCompanyDto::setCountry),
    COMPANY_PHONE("Company phone", XingCompanyDto::setCompanyPhone),
    FAX("Fax", XingCompanyDto::setFax),
    COMPANY_EMAIL("Company email", XingCompanyDto::setCompanyEmail),
    INFORMATION("Information", XingCompanyDto::setInformation),
    EMPLOYEES_LISTED("Employees listed", XingCompanyDto::setEmployeesListed),
    EMPLOYEES_PAGE("Employees page", XingCompanyDto::setEmployeesPage),
    COMPANY_PROFILE_LINK("Company profile link", XingCompanyDto::setCompanyProfileLink),
    YEAR_FOUND("Year found", XingCompanyDto::setYearFound),
    PRODUCTS_AND_SERVICES("Products and services", XingCompanyDto::setProductsAndServices);

    private String columnName;
    private BiConsumer<XingCompanyDto, String> setter;

    XingColumn(String columnName, BiConsumer<XingCompanyDto, String> setter) {
        this.columnName = columnName;
        this.setter = setter;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public BiConsumer<XingCompanyDto, String> getSetter() {
        return setter;
    }
}
