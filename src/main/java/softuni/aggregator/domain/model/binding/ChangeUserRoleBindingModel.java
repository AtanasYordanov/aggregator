package softuni.aggregator.domain.model.binding;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeUserRoleBindingModel {

    private Long userId;
    private String roleName;
}
