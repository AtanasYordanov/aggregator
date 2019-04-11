package softuni.aggregator.domain.model.binding;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportBindingModel {

    private String exportName;
    private Boolean includeCompanies;
}
