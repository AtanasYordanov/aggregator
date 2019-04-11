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
@Table(name = "minor_industries")
public class MinorIndustry extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "major_industry_id", referencedColumnName = "id")
    private MajorIndustry majorIndustry;

    @OneToMany(mappedBy = "industry")
    private List<Company> companies;

    public MinorIndustry(String name, MajorIndustry majorIndustry) {
        this.name = name;
        this.setMajorIndustry(majorIndustry);
    }

    public void setMajorIndustry(MajorIndustry majorIndustry) {
        this.majorIndustry = majorIndustry;
        majorIndustry.addMinorIndustry(this);
    }
}
