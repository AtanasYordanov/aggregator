package softuni.aggregator.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.aggregator.domain.entities.Employee;
import softuni.aggregator.domain.entities.MinorIndustry;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @EntityGraph(attributePaths = "company")
    @Query("SELECT e FROM Employee AS e WHERE e.id = :id")
    Optional<Employee> findByIdEager(@Param("id") Long id);

    @EntityGraph(attributePaths = "company", type = LOAD)
    @Query("SELECT e FROM Employee AS e")
    List<Employee> findAllEager();

    @EntityGraph(attributePaths = "company")
    List<Employee> findByEmailIn(List<String> emails);

    @Query("SELECT e FROM Employee e WHERE e.company.industry IN :industries")
    List<Employee> getEmployeesPageForIndustry(Pageable pageable, List<MinorIndustry> industries);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.company.industry IN :industries")
    long getCompaniesCountForIndustry(List<MinorIndustry> industries);

    @Query("SELECT e FROM Employee e WHERE e.company.industry IN :industries")
    List<Employee> findAllByIndustryIn(List<MinorIndustry> industries);

    @EntityGraph(attributePaths = "company")
    @Query("SELECT e FROM Employee e WHERE e.company.industry IN :industries")
    List<Employee> findAllByIndustryInEager(List<MinorIndustry> industries);
}
