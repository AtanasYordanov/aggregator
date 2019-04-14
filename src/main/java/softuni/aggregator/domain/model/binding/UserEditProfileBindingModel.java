package softuni.aggregator.domain.model.binding;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.constants.ErrorMessages;
import softuni.aggregator.constants.Validation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserEditProfileBindingModel {

    @NotNull(message = ErrorMessages.INVALID_EMAIL)
    @Size(min = Validation.MIN_EMAIL_LENGTH, message = ErrorMessages.INVALID_EMAIL)
    @Pattern(regexp = Validation.VALID_EMAIL, message = ErrorMessages.INVALID_EMAIL)
    private String email;

    @NotNull(message = ErrorMessages.INVALID_FIRST_NAME)
    @Size(min = Validation.MIN_NAME_LENGTH, message = ErrorMessages.INVALID_FIRST_NAME)
    private String firstName;

    @NotNull(message = ErrorMessages.INVALID_LAST_NAME)
    @Size(min = Validation.MIN_NAME_LENGTH, message = ErrorMessages.INVALID_LAST_NAME)
    private String lastName;
}
