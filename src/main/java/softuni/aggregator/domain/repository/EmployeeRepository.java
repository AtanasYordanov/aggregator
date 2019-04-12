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
            "WHERE (e.company.industry IN :industries OR :industries IS NULL)")
    List<Employee> getFilteredEmployeesPage(Pageable pageable, List<SubIndustry> industries);

    @Query("SELECT COUNT(e.id) FROM Employee e " +
            "WHERE (e.company.industry IN :industries  OR :industries IS NULL)")
    long getFilteredEmployeesCount(List<SubIndustry> industries);

    @EntityGraph(attributePaths = {"company", "company.industry", "company.companyEmails"})
    @Query("SELECT e FROM Employee e WHERE " +
            "(e.company.industry IN :industries OR :industries IS NULL)")
    List<Employee> getFilteredEmployees(List<SubIndustry> industries);
}
