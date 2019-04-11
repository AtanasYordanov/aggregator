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

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findAllByWebsiteIn(List<String> website);

    List<Company> findAllByIndustryIn(List<MinorIndustry> industries);

    @Query("SELECT c FROM Company c WHERE c.industry IN :industries")
    Page<Company> getCompaniesPageForIndustry(Pageable pageable, @Param("industries") List<MinorIndustry> industries);

    @Query(value = "SELECT COUNT(c.id) FROM Company c WHERE c.industry IN :industries")
    long getCompaniesCountForIndustry(@Param("industries") List<MinorIndustry> industries);

    List<Company> findByNameIn(List<String> emails);
}
