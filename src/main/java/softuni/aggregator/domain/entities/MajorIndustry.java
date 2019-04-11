package softuni.aggregator.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "major_industries")
public class MajorIndustry extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "majorIndustry")
    private List<MinorIndustry> minorIndustries;

    public MajorIndustry(String name) {
        this.name = name;
        this.minorIndustries = new ArrayList<>();
    }

    public void addMinorIndustry(MinorIndustry minorIndustry) {
        this.minorIndustries.add(minorIndustry);
    }
}
