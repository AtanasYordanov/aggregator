package softuni.aggregator.utils.performance;

import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.enums.UserRole;

public final class CustomStringUtils {

    public static String truncate(String str, int symbols) {
        return str != null && str.length() > symbols ? str.substring(0, symbols) + "..." : str;
    }

    public static String getUserRole(User user) {
        return UserRole.valueOf(user.getAuthorities().iterator().next().getAuthority()).getName();
    }
}
