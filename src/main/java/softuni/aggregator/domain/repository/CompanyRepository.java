package softuni.aggregator.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.SubIndustry;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findAllByWebsiteIn(List<String> website);

    @Query("SELECT c FROM Company c " +
            "WHERE (c.industry IN :industries OR :industries IS NULL)")
    Page<Company> getFilteredCompaniesPage(Pageable pageable, @Param("industries") List<SubIndustry> industries);

    @Query(value = "SELECT COUNT(c.id) FROM Company c " +
            "WHERE (c.industry IN :industries OR :industries IS NULL)")
    long getFilteredCompaniesCount(@Param("industries") List<SubIndustry> industries);

    @Query("SELECT c FROM Company c " +
            "WHERE (c.industry IN :industries OR :industries IS NULL)")
    List<Company> getFilteredCompanies(List<SubIndustry> industries);
}
