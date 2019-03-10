package softuni.aggregator.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "companies")
public class Company extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "website", unique = true)
    private String website;

    @Column(name = "company_email", unique = true)
    private String companyEmail;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "industry_id", referencedColumnName = "id")
    private MinorIndustry industry;

    @OneToMany(mappedBy = "company")
    private List<Employee> employees;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "company_details", referencedColumnName = "id")
    private CompanyDetails companyDetails;
}
