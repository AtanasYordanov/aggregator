package softuni.aggregator.utils.performance;

import lombok.extern.java.Log;

@Log
public class PerformanceUtils {

    private static final String DEFAULT_NAME = "Performance Test";

    public static void logExecutionSpeed(Runnable func) {
        logExecutionSpeed(func, DEFAULT_NAME);
    }

    public static void logExecutionSpeed(Runnable func, String name) {
        long start = System.currentTimeMillis();
        func.run();
        long end = System.currentTimeMillis();
        double seconds = (end - start) / 1000.0;
        log.severe(String.format("%s - Execution time: %.1f seconds.", name, seconds));
    }
}
