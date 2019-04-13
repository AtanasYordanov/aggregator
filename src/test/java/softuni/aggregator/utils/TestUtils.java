package softuni.aggregator.utils;

import softuni.aggregator.domain.entities.Role;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.enums.UserRole;

import java.util.List;
import java.util.Set;

public class TestUtils {

    private static User loggedUser;

    public static User getLoggedUser() {
        if (loggedUser == null) {
            initUser();
        }
        loggedUser.setAuthorities(Set.of(new Role(UserRole.ROLE_USER.toString())));
        return loggedUser;
    }

    public static User getLoggedModerator() {
        if (loggedUser == null) {
            initUser();
        }
        loggedUser.setAuthorities(Set.of(new Role(UserRole.ROLE_MODERATOR.toString())));
        return loggedUser;
    }

    public static User getLoggedAdmin() {
        if (loggedUser == null) {
            initUser();
        }
        loggedUser.setAuthorities(Set.of(new Role(UserRole.ROLE_ADMIN.toString())));
        return loggedUser;
    }

    private static void initUser() {
        loggedUser = new User();
        loggedUser.setId(5L);
        loggedUser.setFirstName("Gosho");
        loggedUser.setLastName("Goshov");
        loggedUser.setEmail("gosho@abv.bg");
        loggedUser.setAuthorities(Set.of(new Role(UserRole.ROLE_USER.toString())));
        loggedUser.setExports(List.of());
        loggedUser.setImports(List.of());
    }
}
