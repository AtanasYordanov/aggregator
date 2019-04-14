package softuni.aggregator.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.aggregator.service.excel.writer.exports.ExportType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "generatedOn", callSuper = false)
@Entity
@Table(name = "exports")
public class Export extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String exportName;

    @Column(name = "file_name", nullable = false)
    private String fileName;

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

    public Export(String exportName, String fileName, ExportType exportType, int itemsCount, User user) {
        this.exportName = exportName;
        this.fileName = fileName;
        this.exportType = exportType;
        this.itemsCount = itemsCount;
        this.user = user;
        this.generatedOn = LocalDateTime.now();
    }
}