package softuni.aggregator.domain.model.vo;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.domain.entities.SubIndustry;
import softuni.aggregator.utils.performance.CustomStringUtils;

@Getter
@Setter
public class CompanyListVO {

    private Long id;
    private String name;
    private String website;
    private String industry;

    public void setName(String name) {
        this.name = CustomStringUtils.truncate(name, 30);
    }

    public void setWebsite(String website) {
        this.website = CustomStringUtils.truncate(website, 25);
    }

    public void setIndustry(SubIndustry industry) {
        if (industry == null) {
            return;
        }
        this.industry = CustomStringUtils.truncate(industry.getName(), 25);
    }
}
