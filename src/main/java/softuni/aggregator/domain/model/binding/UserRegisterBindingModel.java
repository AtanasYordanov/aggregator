package softuni.aggregator.domain.model.binding;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.domain.model.validation.UniqueEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserRegisterBindingModel {

    @NotNull(message = "Invalid email!")
    @Size(min = 6, message = "Invalid email!")
    @UniqueEmail
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+$", message = "Invalid email!")
    private String email;

    @NotNull(message = "Invalid password!")
    @Size(min = 3, message = "Invalid password!")
    private String password;

    @NotNull(message = "Invalid password!")
    @Size(min = 3, message = "Invalid password!")
    private String confirmPassword;

    @NotNull(message = "Invalid first name!")
    @Size(min = 2, message = "Invalid first name!")
    private String firstName;

    @NotNull(message = "Invalid last name!")
    @Size(min = 2, message = "Invalid last name!")
    private String lastName;
}
