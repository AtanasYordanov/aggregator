package softuni.aggregator.service.excel.writer.model;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.domain.entities.Company;
import softuni.aggregator.domain.entities.Employee;

@Getter
@Setter
public class EmployeesExportDto extends ExcelExportDto {

    private String companyName;
    private String fullName;
    private String position;
    private String email;
    private Integer hunterIoScore;

    public EmployeesExportDto(Employee employee) {
        setCompanyName(employee.getCompany());
        setFullName(employee.getFullName());
        setEmail(employee.getEmail());
        setPosition(employee.getPosition());
        setHunterIoScore(employee.getHunterIoScore());
    }

    private void setCompanyName(Company company) {
        this.companyName = company != null ? company.getName() : null;
    }
}
