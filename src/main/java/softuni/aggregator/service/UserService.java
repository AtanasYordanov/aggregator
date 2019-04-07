package softuni.aggregator.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.binding.UserRegisterBindingModel;
import softuni.aggregator.domain.model.vo.UserDetailsVO;
import softuni.aggregator.domain.model.vo.UserListVO;

import java.util.List;

public interface UserService extends UserDetailsService {

    void registerUser(UserRegisterBindingModel userModel);

    void updateRole(String username, String role);

    boolean existsByEmail(String username);

    void saveUser(User user);

    void updateUserStatus();

    List<UserListVO> getUsersPage(Pageable pageable);

    long getTotalUsersCount();

    UserDetailsVO getUser(Long id);
}
