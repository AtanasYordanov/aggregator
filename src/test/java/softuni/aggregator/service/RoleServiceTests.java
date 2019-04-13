package softuni.aggregator.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import softuni.aggregator.domain.entities.Role;
import softuni.aggregator.domain.enums.UserRole;
import softuni.aggregator.domain.repository.RoleRepository;
import softuni.aggregator.service.impl.RoleServiceImpl;
import softuni.aggregator.web.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTests {

    @Mock
    private RoleRepository mockRoleRepository;

    private RoleService roleService;

    @Before
    public void init() {
        roleService = new RoleServiceImpl(mockRoleRepository);
    }

    @Test
    public void getRoleByName_validRoleName_shouldReturnRole() {
        String testName = "testRoleName";
        Role role = new Role(UserRole.ROLE_USER.toString());
        Mockito.when(mockRoleRepository.findByName(testName)).thenReturn(Optional.of(role));

        Role receivedRole = roleService.getRoleByName(testName);

        Assert.assertEquals(role, receivedRole);
    }

    @Test(expected = NotFoundException.class)
    public void getRoleByName_nonexistentRole_shouldThrowNotFoundException() {
        String testName = "testRoleName";
        Mockito.when(mockRoleRepository.findByName(testName)).thenReturn(Optional.empty());

        roleService.getRoleByName(testName);
    }

    @Test
    public void getModifiableRoles_always_shouldReturnAllRolesExceptRootAdmin() {
        List<String> modifiableRoles = roleService.getModifiableRoles();

        Assert.assertEquals(UserRole.values().length, modifiableRoles.size() + 1);
        Assert.assertTrue(modifiableRoles.contains(UserRole.ROLE_USER.getName()));
        Assert.assertTrue(modifiableRoles.contains(UserRole.ROLE_MODERATOR.getName()));
        Assert.assertTrue(modifiableRoles.contains(UserRole.ROLE_ADMIN.getName()));
        Assert.assertFalse(modifiableRoles.contains(UserRole.ROLE_ROOT_ADMIN.getName()));
    }
}
