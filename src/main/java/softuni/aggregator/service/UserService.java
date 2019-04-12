package softuni.aggregator.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.binding.ChangeUserRoleBindingModel;
import softuni.aggregator.domain.model.binding.UserChangePasswordBindingModel;
import softuni.aggregator.domain.model.binding.UserEditProfileBindingModel;
import softuni.aggregator.domain.model.binding.UserRegisterBindingModel;
import softuni.aggregator.domain.model.vo.UserDetailsVO;
import softuni.aggregator.domain.model.vo.UserListVO;

import java.util.List;

public interface UserService extends UserDetailsService {

    void registerUser(UserRegisterBindingModel userModel);

    void updateRole(ChangeUserRoleBindingModel bindingModel);

    boolean existsByEmail(String username);

    void saveUser(User user);

    void updateUserStatus();

    List<UserListVO> getUsersPage(Pageable pageable);

    long getTotalUsersCount();

    UserDetailsVO getUserDetails(Long id);

    void updateProfile(User loggedUser, UserEditProfileBindingModel bindingModel);

    void updatePassword(User loggedUser, UserChangePasswordBindingModel bindingModel, BindingResult bindingResult);
}
