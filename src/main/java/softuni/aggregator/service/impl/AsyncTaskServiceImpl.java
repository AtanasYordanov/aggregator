package softuni.aggregator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.model.AsyncTask;
import softuni.aggregator.domain.model.AsyncTaskType;
import softuni.aggregator.service.AsyncTaskService;
import softuni.aggregator.utils.performance.PerformanceUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {

    private final Map<Long, List<AsyncTask>> tasks = new HashMap<>();

    @Override
    public void registerTask(Long userId, AsyncTaskType taskType) {
        tasks.putIfAbsent(userId, new ArrayList<>());
        tasks.get(userId).add(new AsyncTask(taskType, LocalDateTime.now()));
        PerformanceUtils.startTimer(String.format("%s - %s", userId, taskType), taskType.getDisplayName());
    }

    @Override
    public void markTaskAsFinished(Long userId, AsyncTaskType taskType, int itemsCount) {
        tasks.getOrDefault(userId, new ArrayList<>()).stream()
                .filter(t -> t.getTaskType().equals(taskType))
                .forEach(t -> {
                    t.setItemsCount(itemsCount);
                    t.setFinishTime(LocalDateTime.now());
                    PerformanceUtils.stopTimer(String.format("%s - %s", userId, t.getTaskType()));
                });
    }

    @Override
    public List<AsyncTask> getFinishedTasks(Long userId) {
        List<AsyncTask> userTasks = tasks.getOrDefault(userId, new ArrayList<>());
        List<AsyncTask> finished = userTasks.stream()
                .filter(t -> t.getFinishTime() != null)
                .collect(Collectors.toList());
        userTasks.removeAll(finished);
        return finished;
    }

    @Override
    public boolean checkForRunningTasks(Long userId) {
        return tasks.get(userId) != null && !tasks.get(userId).isEmpty();
    }

    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    private void clearOldFinishedTasks() {
        tasks.values().forEach(tasks ->
                tasks.removeIf(t -> t.getFinishTime() != null
                        && t.getFinishTime().isBefore(LocalDateTime.now().minusSeconds(5))));
    }
}
