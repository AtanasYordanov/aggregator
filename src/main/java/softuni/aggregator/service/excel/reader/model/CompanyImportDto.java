package softuni.aggregator.service.excel.reader.model;

import lombok.Getter;
import lombok.Setter;

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
        if (website.startsWith("http://")) {
            website = website.substring(7);
        } else if (website.startsWith("https://")) {
            website = website.substring(8);
        }
        if (website.endsWith("/")) {
            website = website.substring(0, website.length() - 1);
        }
        this.website = website;
    }
}
