package softuni.aggregator.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.aggregator.domain.entities.MajorIndustry;

import java.util.Optional;

@Repository
public interface MajorIndustryRepository extends JpaRepository<MajorIndustry, Long> {

    Optional<MajorIndustry> findByName(String name);
}
