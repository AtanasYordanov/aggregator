package softuni.aggregator.service.excel.reader.model;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.service.excel.constants.ExcelConstants;

@Getter
@Setter
public abstract class CompanyImportDto extends ExcelImportDto {

    private String name;
    private String website;
    private String postcode;
    private String city;
    private String country;
    private String companyPhone;
    private String companyEmail;

    public void setWebsite(String website) {
        if (website.startsWith(ExcelConstants.HTTP_PREFIX)) {
            website = website.substring(ExcelConstants.HTTP_PREFIX.length());
        } else if (website.startsWith(ExcelConstants.HTTPS_PREFIX)) {
            website = website.substring(ExcelConstants.HTTPS_PREFIX.length());
        }
        if (website.endsWith(ExcelConstants.SLASH_SUFFIX)) {
            website = website.substring(0, website.length() - ExcelConstants.SLASH_SUFFIX.length());
        }
        this.website = website;
    }
}
