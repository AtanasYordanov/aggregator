package softuni.aggregator.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.aggregator.domain.entities.Import;
import softuni.aggregator.domain.entities.User;

import java.util.List;

@Repository
public interface ImportRepository extends JpaRepository<Import, Long> {

    long countByUser(User user);

    List<Import> findAllByUser(User user, Pageable pageable);
}
