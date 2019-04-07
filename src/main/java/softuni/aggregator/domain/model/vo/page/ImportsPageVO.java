package softuni.aggregator.domain.model.vo.page;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.domain.model.vo.ImportListVO;

import java.util.List;

@Getter
@Setter
public class ImportsPageVO extends BasePageVO {

    private List<ImportListVO> imports;
}
