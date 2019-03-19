package softuni.aggregator.utils.performance;

public class PerformanceUtils {

    private static final String DEFAULT_NAME = "Performance Test";

    public static void logExecutionTime(Runnable runnable) {
        logExecutionTime(runnable, DEFAULT_NAME);
    }

    public static void logExecutionTime(Runnable runnable, String name) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        double seconds = (end - start) / 1000.0;
        System.out.println(String.format("%s - Execution time: %.2f seconds.", name, seconds));
    }
}
