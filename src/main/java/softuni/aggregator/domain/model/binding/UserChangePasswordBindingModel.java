package softuni.aggregator.domain.model.binding;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserChangePasswordBindingModel {

    @NotNull(message = "Invalid old password!")
    @Size(min = 3, message = "Invalid old password!")
    private String oldPassword;

    @NotNull(message = "Invalid new password!")
    @Size(min = 3, message = "Invalid new password!")
    private String newPassword;

    @NotNull(message = "Invalid new password!")
    @Size(min = 3, message = "Invalid new password!")
    private String confirmNewPassword;
}
