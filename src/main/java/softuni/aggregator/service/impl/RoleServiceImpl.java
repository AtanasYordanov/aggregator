package softuni.aggregator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.aggregator.domain.entities.Role;
import softuni.aggregator.domain.repository.RoleRepository;
import softuni.aggregator.domain.enums.UserRole;
import softuni.aggregator.service.RoleService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

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
        return roleRepository.findByName(name).orElseThrow();
    }

    @PostConstruct
    private void seedRoles() {
        if (roleRepository.count() == 0) {
            Role rootAdminRole = new Role(UserRole.ROLE_ROOT_ADMIN.toString());
            Role adminRole = new Role(UserRole.ROLE_ADMIN.toString());
            Role moderatorRole = new Role(UserRole.ROLE_MODERATOR.toString());
            Role userRole = new Role(UserRole.ROLE_USER.toString());

            roleRepository.save(rootAdminRole);
            roleRepository.save(adminRole);
            roleRepository.save(moderatorRole);
            roleRepository.save(userRole);
        }
    }
}
