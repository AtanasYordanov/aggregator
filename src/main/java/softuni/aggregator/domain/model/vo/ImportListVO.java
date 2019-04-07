package softuni.aggregator.domain.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ImportListVO {

    private LocalDateTime date;
    private String type;
    private int totalItemsCount;
    private int newEntriesCount;
    private String userEmail;
}
