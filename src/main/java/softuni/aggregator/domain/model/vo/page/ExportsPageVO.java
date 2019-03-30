package softuni.aggregator.domain.model.vo.page;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.domain.model.vo.ExportListVO;

import java.util.List;

@Getter
@Setter
public class ExportsPageVO extends BasePageVO {

    private List<ExportListVO> exports;
}
