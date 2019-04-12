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
@Table(name = "sub_industries")
public class SubIndustry extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "main_industry_id", referencedColumnName = "id")
    private MainIndustry mainIndustry;

    @OneToMany(mappedBy = "industry")
    private List<Company> companies;

    public SubIndustry(String name, MainIndustry mainIndustry) {
        this.name = name;
        this.mainIndustry = mainIndustry;
    }
}
