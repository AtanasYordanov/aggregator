package softuni.aggregator.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.MinorIndustry;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findAllByWebsiteIn(List<String> website);

    Optional<Company> findByName(String website);

    List<Company> findAllByIndustryIn(List<MinorIndustry> industries);

    @Query("SELECT c FROM Company AS c " +
            "WHERE c.industry IN :industries")
    Page<Company> getCompaniesPageForIndustry(Pageable pageable, @Param("industries") List<MinorIndustry> industries);

    @Query(value = "SELECT COUNT(c.id) FROM companies AS c " +
            "WHERE c.industry_id IN :industries", nativeQuery = true)
    long getCompaniesCountForIndustry(@Param("industries") List<MinorIndustry> industries);
}
