package softuni.aggregator.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.aggregator.domain.entities.Export;

@Repository
public interface ExportRepository extends JpaRepository<Export, Long> {
}
