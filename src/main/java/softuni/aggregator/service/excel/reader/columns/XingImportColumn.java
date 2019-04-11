package softuni.aggregator.service.excel.reader.columns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import softuni.aggregator.service.excel.reader.model.XingCompanyImportDto;

import java.util.function.BiConsumer;

@Getter
@AllArgsConstructor
public enum XingImportColumn implements ReadExcelColumn<XingCompanyImportDto> {

    XING_INDUSTRY_1("Xing industry 1", XingCompanyImportDto::setXingIndustry1),
    XING_INDUSTRY_2("Xing industry 2", XingCompanyImportDto::setXingIndustry2),
    COMPANY_NAME("Company name", XingCompanyImportDto::setName),
    WEBSITE("Website", XingCompanyImportDto::setWebsite),
    EMPLOYEES_RANGE("Employee range", XingCompanyImportDto::setEmployeesRange),
    STREET("Street", XingCompanyImportDto::setStreet),
    POSTCODE("Postcode", XingCompanyImportDto::setPostcode),
    CITY("City", XingCompanyImportDto::setCity),
    COUNTRY("Country", XingCompanyImportDto::setCountry),
    COMPANY_PHONE("Company phone", XingCompanyImportDto::setCompanyPhone),
    FAX("Fax", XingCompanyImportDto::setFax),
    COMPANY_EMAIL("Company email", XingCompanyImportDto::setCompanyEmail),
    INFORMATION("Information", XingCompanyImportDto::setInformation),
    EMPLOYEES_LISTED("Employees listed", XingCompanyImportDto::setEmployeesListed),
    EMPLOYEES_PAGE("Employees page", XingCompanyImportDto::setEmployeesPage),
    COMPANY_PROFILE_LINK("Xing profile", XingCompanyImportDto::setXingProfileLink),
    YEAR_FOUND("Year found", XingCompanyImportDto::setYearFound),
    PRODUCTS_AND_SERVICES("Products and services", XingCompanyImportDto::setProductsAndServices);

    private String columnName;
    private BiConsumer<XingCompanyImportDto, String> setter;
}
