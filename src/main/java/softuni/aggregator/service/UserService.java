package softuni.aggregator.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.binding.UserRegisterBindingModel;

public interface UserService extends UserDetailsService {

    void registerUser(UserRegisterBindingModel userModel);

    void updateRole(String username, String role);

    boolean existsByEmail(String username);

    void saveUser(User user);

    void updateUserStatus();
}
