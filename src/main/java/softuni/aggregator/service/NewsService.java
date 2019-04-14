package softuni.aggregator.service;

import softuni.aggregator.domain.model.vo.FinishedTaskVO;

import java.util.List;

public interface NewsService {

    void registerTask(Long id, String taskType);

    void markTaskAsFinished(Long id, String taskType, int itemsCount);

    List<FinishedTaskVO> checkForFinishedTasks(Long id);

    boolean checkForRunningTasks(Long userId);
}
