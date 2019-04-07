package softuni.aggregator.domain.model.vo;

import lombok.Getter;
import lombok.Setter;
import softuni.aggregator.domain.enums.UserStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDetailsVO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private int importsCount;
    private int exportsCount;
    private LocalDateTime lastLogin;
    private UserStatus status;
}
