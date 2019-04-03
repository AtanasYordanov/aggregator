package softuni.aggregator.utils.performance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PerformanceUtils {

    private static Map<String, Timer> timers = new HashMap<>();

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

    public static void startTimer(String timerId, String timerName) {
        timers.putIfAbsent(timerId, new Timer(timerName, System.currentTimeMillis()));
    }

    public static void startTimer(String timerName) {
        startTimer(timerName, timerName);
    }

    public static void stopTimer(String timerId) {
        Timer timer = timers.remove(timerId);
        long start = timer.getStart();
        long end = System.currentTimeMillis();
        log.info(String.format("%s - Execution time: %.2f seconds.", timer.getName(), getSeconds(start, end)));
    }

    private static double getSeconds(long start, long end) {
        return (end - start) / 1000.0;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class Timer {
        private String name;
        private Long start;
    }
}
