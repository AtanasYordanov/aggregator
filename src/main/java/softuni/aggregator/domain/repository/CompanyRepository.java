package softuni.aggregator.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.SubIndustry;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findAllByWebsiteIn(List<String> website);

    @Query("SELECT c FROM Company c " +
            "WHERE (c.industry IN :industries OR :industries IS NULL) " +
            "AND ((:includeCompaniesWithNoEmployeeData = TRUE AND c.employeesCount IS NULL) OR (c.employeesCount >= :minEmployees AND c.employeesCount <= :maxEmployees)) " +
            "AND (:yearFound IS NULL OR c.yearFound = :yearFound) " +
            "AND (:country IS NULL OR :country = '' OR LOWER(c.country) = LOWER(:country)) " +
            "AND (:city IS NULL OR :city = '' OR LOWER(c.city) = LOWER(:city))")
    Page<Company> getFilteredCompaniesPage(Pageable pageable, List<SubIndustry> industries, Integer minEmployees, Integer maxEmployees,
                                           Boolean includeCompaniesWithNoEmployeeData, Integer yearFound, String country, String city);

    @Query(value = "SELECT COUNT(c.id) FROM Company c " +
            "WHERE (c.industry IN :industries OR :industries IS NULL) " +
            "AND ((c.employeesCount >= :minEmployees AND c.employeesCount <= :maxEmployees) OR (:includeCompaniesWithNoEmployeeData = TRUE AND c.employeesCount IS NULL)) " +
            "AND (:yearFound IS NULL OR c.yearFound = :yearFound) " +
            "AND (:country IS NULL OR :country = '' OR LOWER(c.country) = LOWER(:country)) " +
            "AND (:city IS NULL OR :city = '' OR LOWER(c.city) = LOWER(:city))")
    long getFilteredCompaniesCount(List<SubIndustry> industries, Integer minEmployees, Integer maxEmployees,
                                   Boolean includeCompaniesWithNoEmployeeData, Integer yearFound, String country, String city);

    @Query("SELECT c FROM Company c " +
            "WHERE (c.industry IN :industries OR :industries IS NULL) " +
            "AND ((c.employeesCount >= :minEmployees AND c.employeesCount <= :maxEmployees) OR (:includeCompaniesWithNoEmployeeData = TRUE AND c.employeesCount IS NULL)) " +
            "AND (:yearFound IS NULL OR c.yearFound = :yearFound) " +
            "AND (:country IS NULL OR :country = '' OR LOWER(c.country) = LOWER(:country)) " +
            "AND (:city IS NULL OR :city = '' OR LOWER(c.city) = LOWER(:city))")
    List<Company> getFilteredCompanies(List<SubIndustry> industries, Integer minEmployees, Integer maxEmployees,
                                       Boolean includeCompaniesWithNoEmployeeData, Integer yearFound, String country, String city);
}
