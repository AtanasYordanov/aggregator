package softuni.aggregator.utils.performance;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PerformanceUtils {

    private static Map<String, Long> timers = new HashMap<>();

    private static final String DEFAULT_NAME = "Performance Test";

    public static void logExecutionTime(Runnable runnable) {
        logExecutionTime(runnable, DEFAULT_NAME);
    }

    public static void logExecutionTime(Runnable runnable, String name) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        log.info(String.format("%s - Execution time: %.2f seconds.", name, getSeconds(start, end)));
    }

    public static void startTimer(String timerName) {
        timers.put(timerName, System.currentTimeMillis());
    }

    public static void stopTimer(String timerName) {
        long start = timers.remove(timerName);
        long end = System.currentTimeMillis();
        log.info(String.format("%s - Execution time: %.2f seconds.", timerName, getSeconds(start, end)));
    }

    private static double getSeconds(long start, long end) {
        return (end - start) / 1000.0;
    }
}
