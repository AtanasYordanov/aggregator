package softuni.aggregator.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.aggregator.service.excel.writer.exports.ExportType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


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

    @Column(name = "items_count", nullable = false)
    private int itemsCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Export(String name, ExportType exportType, int itemsCount, User user) {
        this.name = name;
        this.exportType = exportType;
        this.itemsCount = itemsCount;
        this.user = user;
        this.generatedOn = LocalDateTime.now(ZoneOffset.UTC);
    }
}