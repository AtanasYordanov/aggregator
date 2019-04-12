package softuni.aggregator.domain.model.vo.page;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterPageVO extends BasePageVO {

    private List<String> subIndustries;
    private List<String> mainIndustries;
}
