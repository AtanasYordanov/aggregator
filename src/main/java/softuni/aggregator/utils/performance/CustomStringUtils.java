package softuni.aggregator.utils.performance;

import softuni.aggregator.domain.entities.User;

public final class CustomStringUtils {

    public static String truncate(String str, int symbols) {
        return str != null && str.length() > symbols ? str.substring(0, symbols) + "..." : str;
    }

    public static String getUserRole(User user) {
        String authority = user.getAuthorities().iterator().next().getAuthority();
        return authority.substring(authority.lastIndexOf("_") + 1);
    }
}
