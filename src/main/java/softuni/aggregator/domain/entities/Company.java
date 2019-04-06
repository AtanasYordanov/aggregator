package softuni.aggregator.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "companies"
        , indexes = @Index(name = "websiteIndex", columnList = "website"))
public class Company extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "website", unique = true)
    private String website;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "company_emails", joinColumns = @JoinColumn(name = "company_id"))
    private List<String> companyEmails;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "industry_id", referencedColumnName = "id")
    private MinorIndustry industry;

    @OneToMany(mappedBy = "company")
    private List<Employee> employees;

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

    @Column(name = "fax")
    private String fax;

    @Column(name = "xing_profile_link")
    private String xingProfileLink;

    @Column(name = "year_found")
    private Integer yearFound;

    @Column(name = "information", columnDefinition = "text")
    private String information;

    @Column(name = "products_and_services", columnDefinition = "text")
    private String productsAndServices;

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

    @Column(name = "employees_page")
    private String employeesPage;

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

    public Company() {
        companyEmails = new ArrayList<>();
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public void setWebsite(String website) {
        if (website != null) {
            this.website = website;
        }
    }

    public void setIndustry(MinorIndustry industry) {
        if (industry != null) {
            this.industry = industry;
        }
    }

    public void addEmail(String email) {
        if (!containsEmail(email)) {
            if (email != null) {
                companyEmails.add(email);
            }
        }
    }

    private boolean containsEmail(String email) {
        for (String companyEmail : companyEmails) {
            if (companyEmail.equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void setEmployeesRange(String employeesRange) {
        if (employeesRange != null) {
            this.employeesRange = employeesRange;
        }
    }

    public void setStreet(String street) {
        if (street != null) {
            this.street = street;
        }
    }

    public void setPostcode(Integer postcode) {
        if (postcode != null) {
            this.postcode = postcode;
        }
    }

    public void setCountry(String country) {
        if (country != null) {
            this.country = country;
        }
    }

    public void setCity(String city) {
        if (city != null) {
            this.city = city;
        }
    }

    public void setCompanyPhone(String companyPhone) {
        if (companyPhone != null) {
            this.companyPhone = companyPhone;
        }
    }

    public void setFax(String fax) {
        if (fax != null) {
            this.fax = fax;
        }
    }

    public void setXingProfileLink(String xingProfileLink) {
        if (xingProfileLink != null) {
            this.xingProfileLink = xingProfileLink;
        }
    }

    public void setYearFound(Integer yearFound) {
        if (yearFound != null) {
            this.yearFound = yearFound;
        }
    }

    public void setInformation(String information) {
        if (information != null) {
            this.information = information;
        }
    }

    public void setProductsAndServices(String productsAndServices) {
        if (productsAndServices != null) {
            this.productsAndServices = productsAndServices;
        }
    }

    public void setVATNumber(String VATNumber) {
        if (VATNumber != null) {
            this.VATNumber = VATNumber;
        }
    }

    public void setBvDIdNumber(String bvDIdNumber) {
        if (bvDIdNumber != null) {
            this.BvDIdNumber = bvDIdNumber;
        }
    }

    public void setISOCountryCode(String ISOCountryCode) {
        if (ISOCountryCode != null) {
            this.ISOCountryCode = ISOCountryCode;
        }
    }

    public void setNaceRevMainSection(String naceRevMainSection) {
        if (naceRevMainSection != null) {
            this.naceRevMainSection = naceRevMainSection;
        }
    }

    public void setNaceRevCoreCode(Integer naceRevCoreCode) {
        if (naceRevCoreCode != null) {
            this.naceRevCoreCode = naceRevCoreCode;
        }
    }

    public void setConsolidationCode(String consolidationCode) {
        if (consolidationCode != null) {
            this.consolidationCode = consolidationCode;
        }
    }

    public void setOperatingIncome(String operatingIncome) {
        if (operatingIncome != null) {
            this.operatingIncome = operatingIncome;
        }
    }

    public void setEmployeesCount(Integer employeesCount) {
        if (employeesCount != null) {
            this.employeesCount = employeesCount;
        }
    }

    public void setAddress(String address) {
        if (address != null) {
            this.address = address;
        }
    }

    public void setJobDescription(String jobDescription) {
        if (jobDescription != null) {
            this.jobDescription = jobDescription;
        }
    }

    public void setStandardizedLegalForm(String standardizedLegalForm) {
        if (standardizedLegalForm != null) {
            this.standardizedLegalForm = standardizedLegalForm;
        }
    }

    public void setManagersCount(Integer managersCount) {
        if (managersCount != null) {
            this.managersCount = managersCount;
        }
    }

    public void setCorporationCompaniesCount(Integer corporationCompaniesCount) {
        if (corporationCompaniesCount != null) {
            this.corporationCompaniesCount = corporationCompaniesCount;
        }
    }

    public void setSubsidiariesCount(Integer subsidiariesCount) {
        if (subsidiariesCount != null) {
            this.subsidiariesCount = subsidiariesCount;
        }
    }
}
