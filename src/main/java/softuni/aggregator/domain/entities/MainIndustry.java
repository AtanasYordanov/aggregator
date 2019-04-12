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
@Table(name = "main_industries")
public class MainIndustry extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "mainIndustry")
    private List<SubIndustry> subIndustries;

    public MainIndustry(String name) {
        this.name = name;
        this.subIndustries = new ArrayList<>();
    }
}
