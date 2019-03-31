package softuni.aggregator.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.aggregator.domain.entities.Export;
import softuni.aggregator.domain.entities.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExportRepository extends JpaRepository<Export, Long> {

    List<Export> findAllByGeneratedOnBefore(LocalDateTime dateTime);

    Page<Export> findAllByUser(User user, Pageable pageable);

    long countByUser(User user);
}
