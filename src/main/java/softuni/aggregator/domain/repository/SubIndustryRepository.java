package softuni.aggregator.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.aggregator.domain.entities.SubIndustry;

import java.util.Optional;

@Repository
public interface SubIndustryRepository extends JpaRepository<SubIndustry, Long> {

    Optional<SubIndustry> findByName(String name);
}
