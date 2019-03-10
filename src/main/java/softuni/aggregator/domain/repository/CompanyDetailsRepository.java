package softuni.aggregator.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.aggregator.domain.entities.CompanyDetails;


@Repository
public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, Long> {
}
