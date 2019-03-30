package softuni.aggregator.domain.model.vo;

import lombok.Getter;
import softuni.aggregator.domain.entities.Company;

@Getter
public class EmployeeListVO {

    private String fullName;
    private String email;
    private String company;

    public void setFullName(String fullName) {
        this.fullName = truncate(fullName, 30);
    }

    public void setEmail(String email) {
        this.email = truncate(email, 30);
    }

    public void setCompanyName(Company company) {
        this.company = company != null ? truncate(company.getName(), 30) : "n/a";
    }

    private String truncate(String str, int symbolCount) {
        return str != null && str.length() > symbolCount ? str.substring(0, symbolCount) + "..." : str;
    }
}
