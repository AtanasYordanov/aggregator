package softuni.aggregator.service;

import softuni.aggregator.domain.entities.Role;

import java.util.List;

public interface RoleService {

    Role getRoleByName(String name);

    List<String> getModifiableRoles();
}
