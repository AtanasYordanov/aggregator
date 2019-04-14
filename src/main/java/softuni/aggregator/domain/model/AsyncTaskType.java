package softuni.aggregator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AsyncTaskType {

    COMPANIES_EXPORT("Companies export"),
    EMPLOYEES_EXPORT("Employees export");

    private String displayName;
}
