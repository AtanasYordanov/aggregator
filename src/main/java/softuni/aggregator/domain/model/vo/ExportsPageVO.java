package softuni.aggregator.domain.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExportsPageVO {

    private List<ExportListVO> exports;
    private long totalExportsCount;
}
