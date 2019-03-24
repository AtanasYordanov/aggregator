package softuni.aggregator.domain.model.vo;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.domain.entities.MinorIndustry;

@Getter
@Setter
public class CompanyListVO {

    private Long id;
    private String name;
    private String website;
    private String industry;

    public void setName(String name) {
        this.name = truncate(name, 30);
    }

    public void setWebsite(String website) {
        this.website = truncate(website, 25);
    }

    public void setIndustry(MinorIndustry industry) {
        if (industry == null) {
            return;
        }
        this.industry = truncate(industry.getName(), 25);
    }

    private String truncate(String str, int symbolCount) {
        return str != null && str.length() > symbolCount ? str.substring(0, symbolCount) + "..." : str;
    }
}
