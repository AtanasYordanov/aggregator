package softuni.aggregator.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "companies", indexes = {@Index(name = "websiteIndex", columnList = "website")})
public class Company extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "website", unique = true)
    private String website;

    @ElementCollection
    @CollectionTable(name = "company_emails", joinColumns = @JoinColumn(name = "company_id"))
    private List<String> companyEmails;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "industry_id", referencedColumnName = "id")
    private MinorIndustry industry;

    @OneToMany(mappedBy = "company")
    private List<Employee> employees;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "company_details", referencedColumnName = "id")
    private CompanyDetails companyDetails;

    public Company() {
        companyEmails = new ArrayList<>();
    }

    public void addEmail(String email) {
        companyEmails.add(email);
    }

    public boolean containsEmail(String email) {
        for (String companyEmail : companyEmails) {
            if (companyEmail.equals(email)) {
                return true;
            }
        }
        return false;
    }
}
