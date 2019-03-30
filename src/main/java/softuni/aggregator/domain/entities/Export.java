package softuni.aggregator.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.aggregator.service.excel.writer.exports.ExportType;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "exports")
public class Export extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "generated_on", nullable = false)
    private LocalDateTime generatedOn;

    @Column(name = "export_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExportType exportType;

    public Export(String name, LocalDateTime generatedOn, ExportType exportType) {
        this.name = name;
        this.generatedOn = generatedOn;
        this.exportType = exportType;
    }

    //    private User creator;
}