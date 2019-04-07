package softuni.aggregator.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Role;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.enums.UserStatus;
import softuni.aggregator.domain.model.binding.UserRegisterBindingModel;
import softuni.aggregator.domain.repository.UserRepository;
import softuni.aggregator.domain.enums.UserRole;
import softuni.aggregator.service.RoleService;
import softuni.aggregator.service.UserService;
import org.springframework.transaction.annotation.Transactional;

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
        return userRepository.findByEmail(email).orElseThrow();
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
    public void updateRole(String email, String role) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Set<Role> authorities = new HashSet<>();
        authorities.add(roleService.getRoleByName(role));
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
    public void updateUserStatus() {
        List<User> users = userRepository.findAll().stream()
                .filter(u -> u.getLastLogin() == null
                        || u.getLastLogin().isBefore(LocalDateTime.now().minusDays(1)))
                .peek(u -> u.setStatus(UserStatus.INACTIVE))
                .collect(Collectors.toList());

        userRepository.saveAll(users);
    }
}
