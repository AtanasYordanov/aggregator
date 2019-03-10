package softuni.aggregator.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "company_details")
public class CompanyDetails extends BaseEntity {

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

    @Column(name = "company_profile_link")
    private String companyProfileLink;

    @Column(name = "year_found")
    private Integer yearFound;

    @Column(name = "information", columnDefinition = "text")
    private String information;

    @Column(name = "vat_number")
    private String VATNumber;

    @Column(name = "bvd_id_number")
    private String BvDIdNumber;

    @Column(name = "iso_country_code")
    private String ISOCountryCode;

    @Column(name = "nace_main_section")
    private String naceRevMainSection;

    @Column(name = "nace_core_code")
    private Integer naceRevCoreCode;

    @Column(name = "consolidation_code")
    private String consolidationCode;

    @Column(name = "operating_income")
    private String operatingIncome;

    @Column(name = "employees_count")
    private Integer employeesCount;

    @Column(name = "address")
    private String address;

    @Column(name = "job_description", columnDefinition = "text")
    private String jobDescription;

    @Column(name = "legal_form")
    private String standardizedLegalForm;

    @Column(name = "managers_count")
    private Integer managersCount;

    @Column(name = "corporation_companies_count")
    private Integer corporationCompaniesCount;

    @Column(name = "subsidiaries_count")
    private Integer subsidiariesCount;

    @OneToOne(mappedBy = "companyDetails")
    private Company company;
}
