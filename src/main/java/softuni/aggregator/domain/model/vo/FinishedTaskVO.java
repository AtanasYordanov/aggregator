package softuni.aggregator.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FinishedTaskVO {

    private String taskName;
    private int itemsCount;
}
