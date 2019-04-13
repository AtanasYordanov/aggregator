package softuni.aggregator.domain.repository;

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
            "WHERE (c.industry IN :industries OR :industries IS NULL) " +
            "AND ((:includeCompaniesWithNoEmployeeData = TRUE AND c.employeesCount IS NULL) OR (c.employeesCount >= :minEmployees AND c.employeesCount <= :maxEmployees)) " +
            "AND (:yearFound IS NULL OR c.yearFound = :yearFound) " +
            "AND (:country IS NULL OR :country = '' OR LOWER(c.country) = LOWER(:country)) " +
            "AND (:city IS NULL OR :city = '' OR LOWER(c.city) = LOWER(:city))")
    List<Company> getFilteredCompaniesPage(Pageable pageable, @Param("industries") List<SubIndustry> industries,
                                           @Param("minEmployees") Integer minEmployees, @Param("maxEmployees") Integer maxEmployees,
                                           @Param("includeCompaniesWithNoEmployeeData") Boolean includeCompaniesWithNoEmployeeData,
                                           @Param("yearFound") Integer yearFound, @Param("country") String country,
                                           @Param("city")String city);

    @Query(value = "SELECT COUNT(c.id) FROM Company c " +
            "WHERE (c.industry IN :industries OR :industries IS NULL) " +
            "AND ((c.employeesCount >= :minEmployees AND c.employeesCount <= :maxEmployees) OR (:includeCompaniesWithNoEmployeeData = TRUE AND c.employeesCount IS NULL)) " +
            "AND (:yearFound IS NULL OR c.yearFound = :yearFound) " +
            "AND (:country IS NULL OR :country = '' OR LOWER(c.country) = LOWER(:country)) " +
            "AND (:city IS NULL OR :city = '' OR LOWER(c.city) = LOWER(:city))")
    long getFilteredCompaniesCount(@Param("industries") List<SubIndustry> industries,
                                   @Param("minEmployees") Integer minEmployees, @Param("maxEmployees") Integer maxEmployees,
                                   @Param("includeCompaniesWithNoEmployeeData") Boolean includeCompaniesWithNoEmployeeData,
                                   @Param("yearFound") Integer yearFound, @Param("country") String country,
                                   @Param("city")String city);

    @Query("SELECT c FROM Company c " +
            "WHERE (c.industry IN :industries OR :industries IS NULL) " +
            "AND ((c.employeesCount >= :minEmployees AND c.employeesCount <= :maxEmployees) OR (:includeCompaniesWithNoEmployeeData = TRUE AND c.employeesCount IS NULL)) " +
            "AND (:yearFound IS NULL OR c.yearFound = :yearFound) " +
            "AND (:country IS NULL OR :country = '' OR LOWER(c.country) = LOWER(:country)) " +
            "AND (:city IS NULL OR :city = '' OR LOWER(c.city) = LOWER(:city))")
    List<Company> getFilteredCompanies(@Param("industries") List<SubIndustry> industries,
                                       @Param("minEmployees") Integer minEmployees, @Param("maxEmployees") Integer maxEmployees,
                                       @Param("includeCompaniesWithNoEmployeeData") Boolean includeCompaniesWithNoEmployeeData,
                                       @Param("yearFound") Integer yearFound, @Param("country") String country,
                                       @Param("city")String city);
}
