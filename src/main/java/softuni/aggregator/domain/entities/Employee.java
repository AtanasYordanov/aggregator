package softuni.aggregator.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "position")
    private String position;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "hunter_io_score")
    private Integer hunterIoScore;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    public void setFullName(String fullName) {
        if (fullName != null) {
            this.fullName = fullName;
        }
    }

    public void setPosition(String position) {
        if (position != null) {
            this.position = position;
        }
    }

    public void setEmail(String email) {
        if (email != null) {
            this.email = email;
        }
    }

    public void setHunterIoScore(Integer hunterIoScore) {
        if (hunterIoScore != null) {
            this.hunterIoScore = hunterIoScore;
        }
    }
}
