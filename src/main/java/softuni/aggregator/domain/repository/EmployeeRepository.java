package softuni.aggregator.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.aggregator.domain.entities.Employee;
import softuni.aggregator.domain.entities.SubIndustry;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @EntityGraph(attributePaths = {"company", "company.industry", "company.companyEmails"})
    @Query("SELECT e FROM Employee AS e WHERE e.id = :id")
    Optional<Employee> findByIdEager(@Param("id") Long id);

    @EntityGraph(attributePaths = {"company"})
    List<Employee> findByEmailIn(List<String> emails);

    @Query("SELECT e FROM Employee e " +
            "WHERE (e.company.industry IN :industries OR :industries IS NULL) " +
            "AND ((:includeCompaniesWithNoEmployeeData = TRUE AND e.company.employeesCount IS NULL) OR (e.company.employeesCount >= :minEmployees AND e.company.employeesCount <= :maxEmployees)) " +
            "AND (:yearFound IS NULL OR e.company.yearFound = :yearFound) " +
            "AND (:country IS NULL OR :country = '' OR LOWER(e.company.country) = LOWER(:country)) " +
            "AND (:city IS NULL OR :city = '' OR LOWER(e.company.city) = LOWER(:city))")
    List<Employee> getFilteredEmployeesPage(Pageable pageable, @Param("industries") List<SubIndustry> industries,
                                            @Param("minEmployees") Integer minEmployees, @Param("maxEmployees") Integer maxEmployees,
                                            @Param("includeCompaniesWithNoEmployeeData") Boolean includeCompaniesWithNoEmployeeData,
                                            @Param("yearFound") Integer yearFound, @Param("country") String country,
                                            @Param("city")String city);

    @Query("SELECT COUNT(e.id) FROM Employee e " +
            "WHERE (e.company.industry IN :industries OR :industries IS NULL) " +
            "AND ((:includeCompaniesWithNoEmployeeData = TRUE AND e.company.employeesCount IS NULL) OR (e.company.employeesCount >= :minEmployees AND e.company.employeesCount <= :maxEmployees)) " +
            "AND (:yearFound IS NULL OR e.company.yearFound = :yearFound) " +
            "AND (:country IS NULL OR :country = '' OR LOWER(e.company.country) = LOWER(:country)) " +
            "AND (:city IS NULL OR :city = '' OR LOWER(e.company.city) = LOWER(:city))")
    long getFilteredEmployeesCount(@Param("industries") List<SubIndustry> industries,
                                   @Param("minEmployees") Integer minEmployees, @Param("maxEmployees") Integer maxEmployees,
                                   @Param("includeCompaniesWithNoEmployeeData") Boolean includeCompaniesWithNoEmployeeData,
                                   @Param("yearFound") Integer yearFound, @Param("country") String country,
                                   @Param("city")String city);

    @EntityGraph(attributePaths = {"company", "company.industry", "company.companyEmails"})
    @Query("SELECT e FROM Employee e " +
            "WHERE (e.company.industry IN :industries OR :industries IS NULL) " +
            "AND ((:includeCompaniesWithNoEmployeeData = TRUE AND e.company.employeesCount IS NULL) OR (e.company.employeesCount >= :minEmployees AND e.company.employeesCount <= :maxEmployees)) " +
            "AND (:yearFound IS NULL OR e.company.yearFound = :yearFound) " +
            "AND (:country IS NULL OR :country = '' OR LOWER(e.company.country) = LOWER(:country)) " +
            "AND (:city IS NULL OR :city = '' OR LOWER(e.company.city) = LOWER(:city))")
    List<Employee> getFilteredEmployees(@Param("industries") List<SubIndustry> industries,
                                        @Param("minEmployees") Integer minEmployees, @Param("maxEmployees") Integer maxEmployees,
                                        @Param("includeCompaniesWithNoEmployeeData") Boolean includeCompaniesWithNoEmployeeData,
                                        @Param("yearFound") Integer yearFound, @Param("country") String country,
                                        @Param("city")String city);
}
