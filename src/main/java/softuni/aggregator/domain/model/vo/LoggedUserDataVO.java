package softuni.aggregator.domain.model.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class LoggedUserDataVO {

    private String firstName;
    private Set<String> roles;

    public void setAuthorities(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles.stream()
                .map(GrantedAuthority::getAuthority)
                .map(r -> r.substring(r.lastIndexOf("_") + 1))
                .collect(Collectors.toSet());
    }
}
