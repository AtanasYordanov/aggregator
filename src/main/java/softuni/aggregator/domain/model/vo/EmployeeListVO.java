package softuni.aggregator.domain.model.vo;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.utils.performance.CustomStringUtils;

@Getter
@Setter
public class EmployeeListVO {

    private Long id;
    private String fullName;
    private String position;
    private String company;
    private String email;

    public void setFullName(String fullName) {
        this.fullName = CustomStringUtils.truncate(fullName, 30);
    }

    public void setPosition(String position) {
        this.position = CustomStringUtils.truncate(position, 30);
    }

    public void setCompany(Company company) {
        this.company = company != null ? CustomStringUtils.truncate(company.getName(), 30) : "n/a";
    }

    public void setEmail(String email) {
        this.email = CustomStringUtils.truncate(email, 30);
    }
}
