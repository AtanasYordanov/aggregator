package softuni.aggregator.domain.model.binding;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.constants.ErrorMessages;
import softuni.aggregator.constants.Validation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserChangePasswordBindingModel {

    @NotNull(message = ErrorMessages.INVALID_OLD_PASSWORD)
    @Size(min = Validation.MIN_PASSWORD_LENGTH, message = ErrorMessages.INVALID_OLD_PASSWORD)
    private String oldPassword;

    @NotNull(message = ErrorMessages.INVALID_NEW_PASSWORD)
    @Size(min = Validation.MIN_PASSWORD_LENGTH, message = ErrorMessages.INVALID_NEW_PASSWORD)
    private String newPassword;

    @NotNull(message = ErrorMessages.INVALID_NEW_PASSWORD)
    @Size(min = Validation.MIN_PASSWORD_LENGTH, message = ErrorMessages.INVALID_NEW_PASSWORD)
    private String confirmNewPassword;
}
