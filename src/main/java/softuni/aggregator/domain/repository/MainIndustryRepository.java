package softuni.aggregator.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.aggregator.domain.entities.MainIndustry;

import java.util.Optional;

@Repository
public interface MainIndustryRepository extends JpaRepository<MainIndustry, Long> {

    Optional<MainIndustry> findByName(String name);
}
