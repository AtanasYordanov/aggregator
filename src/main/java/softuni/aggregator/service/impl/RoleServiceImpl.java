package softuni.aggregator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.constants.ErrorMessages;
import softuni.aggregator.domain.entities.Role;
import softuni.aggregator.domain.repository.RoleRepository;
import softuni.aggregator.domain.enums.UserRole;
import softuni.aggregator.service.RoleService;
import org.springframework.transaction.annotation.Transactional;
import softuni.aggregator.web.exceptions.NotFoundException;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.ROLE_NOT_FOUND));
    }

    @Override
    public List<String> getModifiableRoles() {
        return Arrays.stream(UserRole.values())
                .filter(role -> !role.equals(UserRole.ROLE_ROOT_ADMIN))
                .map(UserRole::getName)
                .collect(Collectors.toList());
    }

    @PostConstruct
    private void seedRoles() {
        if (roleRepository.count() == 0) {
            List<Role> roles = Arrays.stream(UserRole.values())
                    .map(ur -> new Role(ur.toString()))
                    .collect(Collectors.toList());

            roleRepository.saveAll(roles);
        }
    }
}
