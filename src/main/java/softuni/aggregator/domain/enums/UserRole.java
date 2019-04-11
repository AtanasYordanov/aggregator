package softuni.aggregator.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    ROLE_ROOT_ADMIN("ROOT ADMIN"),
    ROLE_ADMIN("ADMIN"),
    ROLE_MODERATOR("MODERATOR"),
    ROLE_USER("USER");

    private String name;
}
