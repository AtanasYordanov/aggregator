package softuni.aggregator.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.aggregator.domain.entities.Export;
import softuni.aggregator.domain.entities.User;

@Repository
public interface ExportRepository extends JpaRepository<Export, Long> {

    Page<Export> findAllByUser(User user, Pageable pageable);

    long countByUser(User user);
}
