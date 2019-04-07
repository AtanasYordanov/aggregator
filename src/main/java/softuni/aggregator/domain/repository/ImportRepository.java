package softuni.aggregator.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.aggregator.domain.entities.Import;
import softuni.aggregator.domain.entities.User;

@Repository
public interface ImportRepository extends JpaRepository<Import, Long> {

    long countByUser(User user);

    Page<Import> findAllByUser(User user, Pageable pageable);
}
