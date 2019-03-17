package softuni.aggregator.utils.performance;

public class PerformanceUtils {

    private static final String DEFAULT_NAME = "Performance Test";

    public static void logExecutionTime(Runnable func) {
        logExecutionTime(func, DEFAULT_NAME);
    }

    public static void logExecutionTime(Runnable func, String name) {
        long start = System.currentTimeMillis();
        func.run();
        long end = System.currentTimeMillis();
        double seconds = (end - start) / 1000.0;
        System.out.println(String.format("%s - Execution time: %.1f seconds.", name, seconds));
    }
}
