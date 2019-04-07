package softuni.aggregator.domain.model.vo;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.domain.enums.UserStatus;
import softuni.aggregator.utils.performance.CustomStringUtils;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserListVO {

    private Long id;
    private String email;
    private LocalDateTime lastLogin;
    private UserStatus status;
    private String role;

    public void setEmail(String email) {
        this.email = CustomStringUtils.truncate(email, 30);
    }
}
