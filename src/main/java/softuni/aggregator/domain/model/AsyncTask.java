package softuni.aggregator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class AsyncTask {

    private AsyncTaskType taskType;
    private int itemsCount;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;

    public AsyncTask(AsyncTaskType taskType, LocalDateTime startTime) {
        this.startTime = startTime;
        this.taskType = taskType;
    }
}
