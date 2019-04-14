package softuni.aggregator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TaskInfoVO {

    List<AsyncTask> finishedTasks;
    boolean runningTasks;
}
