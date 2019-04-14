package softuni.aggregator.domain.model.binding;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.constants.ErrorMessages;
import softuni.aggregator.constants.Validation;
import softuni.aggregator.domain.model.validation.UniqueEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserRegisterBindingModel {

    @NotNull(message = ErrorMessages.INVALID_EMAIL)
    @UniqueEmail
    @Size(min = Validation.MIN_EMAIL_LENGTH, message = ErrorMessages.INVALID_EMAIL)
    @Pattern(regexp = Validation.VALID_EMAIL, message = ErrorMessages.INVALID_EMAIL)
    private String email;

    @NotNull(message = ErrorMessages.INVALID_PASSWORD)
    @Size(min = Validation.MIN_PASSWORD_LENGTH, message = ErrorMessages.INVALID_PASSWORD)
    private String password;

    @NotNull(message = ErrorMessages.INVALID_PASSWORD)
    @Size(min = Validation.MIN_PASSWORD_LENGTH, message = ErrorMessages.INVALID_PASSWORD)
    private String confirmPassword;

    @NotNull(message = ErrorMessages.INVALID_FIRST_NAME)
    @Size(min = Validation.MIN_NAME_LENGTH, message = ErrorMessages.INVALID_FIRST_NAME)
    private String firstName;

    @NotNull(message = ErrorMessages.INVALID_LAST_NAME)
    @Size(min = Validation.MIN_NAME_LENGTH, message = ErrorMessages.INVALID_LAST_NAME)
    private String lastName;
}
