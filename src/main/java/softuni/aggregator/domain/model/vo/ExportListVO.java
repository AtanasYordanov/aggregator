package softuni.aggregator.domain.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExportListVO {

    private Long id;
    private LocalDateTime generatedOn;
    private String type;
}
