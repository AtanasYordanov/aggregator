package softuni.aggregator.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Role;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.enums.UserStatus;
import softuni.aggregator.domain.model.binding.ChangeUserRoleBindingModel;
import softuni.aggregator.domain.model.binding.UserRegisterBindingModel;
import softuni.aggregator.domain.model.vo.UserDetailsVO;
import softuni.aggregator.domain.model.vo.UserListVO;
import softuni.aggregator.domain.repository.UserRepository;
import softuni.aggregator.domain.enums.UserRole;
import softuni.aggregator.service.RoleService;
import softuni.aggregator.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import softuni.aggregator.utils.performance.CustomStringUtils;
import softuni.aggregator.web.exceptions.ForbiddenActionException;
import softuni.aggregator.web.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService,
                           BCryptPasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("No such user."));
    }

    @Override
    public void registerUser(UserRegisterBindingModel userModel) {
        User user = mapper.map(userModel, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.ACTIVE);

        Set<Role> authorities = new HashSet<>();
        Role role = userRepository.count() == 0
                ? roleService.getRoleByName(UserRole.ROLE_ROOT_ADMIN.toString())
                : roleService.getRoleByName(UserRole.ROLE_USER.toString());
        authorities.add(role);
        user.setAuthorities(authorities);

        saveUser(user);
    }

    @Override
    public void updateRole(ChangeUserRoleBindingModel bindingModel) {
        User user = userRepository.findById(bindingModel.getUserId())
                .orElseThrow(() -> new NotFoundException("No such user."));

        if (userIsRootAdmin(user)) {
            throw new ForbiddenActionException("You cannot change the ROOT ADMIN's role.");
        }

        Set<Role> authorities = new HashSet<>();
        Role role = getUserRole(bindingModel.getRoleName());
        authorities.add(role);
        user.setAuthorities(authorities);
        saveUser(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<UserListVO> getUsersPage(Pageable pageable) {
        return userRepository.findAll(pageable).stream()
                .map(u ->  {
                    UserListVO userVO = mapper.map(u, UserListVO.class);
                    userVO.setRole(CustomStringUtils.getUserRole(u));
                    return userVO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalUsersCount() {
        return userRepository.count();
    }

    @Override
    public UserDetailsVO getUserDetails(Long id) {
        return userRepository.findById(id)
                .map(u -> {
                    UserDetailsVO userDetailsVO = mapper.map(u, UserDetailsVO.class);
                    userDetailsVO.setRole(CustomStringUtils.getUserRole(u));
                    userDetailsVO.setImportsCount(u.getImports().size());
                    userDetailsVO.setExportsCount(u.getExports().size());
                    return userDetailsVO;
                })
                .orElseThrow(() -> new NotFoundException("No such user."));
    }

    @Override
    public void updateUserStatus() {
        List<User> users = userRepository.findAll().stream()
                .filter(u -> u.getLastLogin() == null
                        || u.getLastLogin().isBefore(LocalDateTime.now().minusDays(1)))
                .peek(u -> u.setStatus(UserStatus.INACTIVE))
                .collect(Collectors.toList());

        userRepository.saveAll(users);
    }

    private boolean userIsRootAdmin(User user) {
        return user.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals(UserRole.ROLE_ROOT_ADMIN.toString()));
    }

    private Role getUserRole(String roleName) {
        for (UserRole role : UserRole.values()) {
            if (role.getName().equals(roleName)) {
                if (!role.equals(UserRole.ROLE_ROOT_ADMIN)) {
                    return roleService.getRoleByName(role.toString());
                } else {
                    throw new ForbiddenActionException("You cannot assign ROOT ADMIN role.");
                }
            }
        }
        throw new NotFoundException("No such role.");
    }
}
