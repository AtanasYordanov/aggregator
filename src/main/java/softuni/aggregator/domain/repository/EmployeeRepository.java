package softuni.aggregator.domain.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.aggregator.domain.entities.Employee;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @EntityGraph(attributePaths = {"company"}, type = LOAD)
    @Query("SELECT e FROM Employee AS e")
    List<Employee> findAllEager();

    Optional<Employee> findByEmail(String email);
}
