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

    @Column(name = "employees_range")
    private String employeesRange;

    @Column(name = "street")
    private String street;

    @Column(name = "postcode")
    private Integer postcode;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "company_phone")
    private String companyPhone;

    @Column(name = "fax", unique = true)
    private String fax;

    @Column(name = "company_email", unique = true)
    private String companyEmail;

    @Column(name = "company_profile_link")
    private String companyProfileLink;

    @Column(name = "year_found")
    private Integer yearFound;

    @Column(name = "information", columnDefinition = "text")
    private String information;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "industry_id", referencedColumnName = "id")
    private MinorIndustry industry;

    @OneToMany(mappedBy = "company")
    private List<Employee> employees;
}
