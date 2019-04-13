package softuni.aggregator.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.aggregator.service.excel.reader.imports.ImportType;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"user", "date"})
@Entity
@Table(name = "imports")
public class Import extends BaseEntity {

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "export_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ImportType importType;

    @Column(name = "total_items_count", nullable = false)
    private int totalItemsCount;

    @Column(name = "new_entries_count", nullable = false)
    private int newEntriesCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Import(User user, ImportType importType, int totalItemsCount, int newEntriesCount) {
        this.user = user;
        this.importType = importType;
        this.totalItemsCount = totalItemsCount;
        this.newEntriesCount = newEntriesCount;
        this.date = LocalDateTime.now();
    }
}