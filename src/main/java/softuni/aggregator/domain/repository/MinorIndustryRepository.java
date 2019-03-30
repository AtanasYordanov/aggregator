package softuni.aggregator.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.aggregator.domain.entities.MajorIndustry;
import softuni.aggregator.domain.entities.MinorIndustry;

import java.util.List;
import java.util.Optional;

@Repository
public interface MinorIndustryRepository extends JpaRepository<MinorIndustry, Long> {

    Optional<MinorIndustry> findByName(String name);

    List<MinorIndustry> findByMajorIndustry(MajorIndustry majorIndustry);
}
