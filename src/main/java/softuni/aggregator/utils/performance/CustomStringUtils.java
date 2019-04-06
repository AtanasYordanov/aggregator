package softuni.aggregator.utils.performance;

public final class CustomStringUtils {

    public static String truncate(String str, int symbols) {
        return str != null && str.length() > symbols ? str.substring(0, symbols) + "..." : str;
    }
}
