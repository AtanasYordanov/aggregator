package softuni.aggregator.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class NewsVO {

    List<FinishedTaskVO> finishedTasks;
    boolean runningTasks;
}
