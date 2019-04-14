package softuni.aggregator.service.impl;

import org.springframework.stereotype.Service;
import softuni.aggregator.domain.model.vo.FinishedTaskVO;
import softuni.aggregator.service.NewsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsServiceImpl implements NewsService {

    private final Map<Long, List<String>> runningTasks = new HashMap<>();
    private final Map<Long, List<FinishedTaskVO>> finishedTasks = new HashMap<>();


    @Override
    public void registerTask(Long userId, String taskType) {
        runningTasks.putIfAbsent(userId, new ArrayList<>());
        runningTasks.get(userId).add(taskType);
    }

    @Override
    public void markTaskAsFinished(Long userId, String taskType, int itemsCount) {
        List<String> taskTypes = runningTasks.get(userId);
        if (taskTypes.contains(taskType)) {
            taskTypes.removeIf(t -> t.equals(taskType));
            finishedTasks.putIfAbsent(userId, new ArrayList<>());
            finishedTasks.get(userId).add(new FinishedTaskVO(taskType, itemsCount));
        }
    }

    @Override
    public List<FinishedTaskVO> checkForFinishedTasks(Long userId) {
        return this.finishedTasks.remove(userId);
    }

    @Override
    public boolean checkForRunningTasks(Long userId) {
        return runningTasks.get(userId) != null && !runningTasks.get(userId).isEmpty();
    }
}
