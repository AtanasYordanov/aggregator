package softuni.aggregator.service;

import softuni.aggregator.domain.model.AsyncTask;
import softuni.aggregator.domain.model.AsyncTaskType;

import java.util.List;

public interface AsyncTaskService {

    void registerTask(Long userId, AsyncTaskType taskType);

    void markTaskAsFinished(Long userId, AsyncTaskType taskType, int itemsCount);

    List<AsyncTask> getFinishedTasks(Long userId);

    boolean checkForRunningTasks(Long userId);
}
