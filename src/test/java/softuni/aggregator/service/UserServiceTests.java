package softuni.aggregator.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import softuni.aggregator.domain.entities.Role;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.enums.UserRole;
import softuni.aggregator.domain.enums.UserStatus;
import softuni.aggregator.domain.model.binding.ChangeUserRoleBindingModel;
import softuni.aggregator.domain.model.binding.UserChangePasswordBindingModel;
import softuni.aggregator.domain.model.binding.UserEditProfileBindingModel;
import softuni.aggregator.domain.model.binding.UserRegisterBindingModel;
import softuni.aggregator.domain.model.vo.UserDetailsVO;
import softuni.aggregator.domain.model.vo.page.UsersPageVO;
import softuni.aggregator.domain.repository.UserRepository;
import softuni.aggregator.service.impl.UserServiceImpl;
import softuni.aggregator.utils.performance.CustomStringUtils;
import softuni.aggregator.web.exceptions.ForbiddenActionException;
import softuni.aggregator.web.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleService mockRoleService;

    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Before
    public void init() {
        ModelMapper mapper = new ModelMapper();
        userService = new UserServiceImpl(mockUserRepository, mockRoleService, passwordEncoder, mapper);
    }

    @Test
    public void loadUserByUsername_validUser_shouldReturnUser() {
        final String testEmail = "pesho@abv.bg";
        User testUser = new User();
        Mockito.when(mockUserRepository.findByEmail(testEmail)).thenReturn(Optional.of(testUser));
        UserDetails received = userService.loadUserByUsername(testEmail);
        Assert.assertEquals(testUser, received);
    }

    @Test(expected = NotFoundException.class)
    public void loadUserByUsername_nonexistentUser_shouldThrowNotFoundException() {
        final String testEmail = "pesho@abv.bg";
        Mockito.when(mockUserRepository.findByEmail(testEmail)).thenReturn(Optional.empty());
        userService.loadUserByUsername(testEmail);
    }

    @Test
    public void registerUser_validData_shouldRegisterUserCorrectly() {
        UserRegisterBindingModel userModel = new UserRegisterBindingModel();
        userModel.setFirstName("Test");
        userModel.setLastName("Testov");
        userModel.setEmail("test@test.test");
        userModel.setPassword("TestPass");

        User expected = new User();
        expected.setFirstName(userModel.getFirstName());
        expected.setLastName(userModel.getLastName());
        expected.setEmail(userModel.getEmail());

        Mockito.when(mockUserRepository.count()).thenReturn(1L);
        Mockito.when(mockRoleService.getRoleByName(UserRole.ROLE_USER.toString()))
                .thenReturn(new Role(UserRole.ROLE_USER.toString()));

        userService.registerUser(userModel);

        Mockito.verify(mockUserRepository).save(expected);
    }

    @Test
    public void updateProfile_validData_shouldUpdateProfileWithCorrectData() {
        UserEditProfileBindingModel userModel = new UserEditProfileBindingModel();
        userModel.setFirstName("Test 2");
        userModel.setLastName("Testov 2");
        userModel.setEmail("test2@test.test");

        User user = new User();
        userService.updateProfile(user, userModel);

        Mockito.verify(mockUserRepository).save(user);

        Assert.assertEquals(userModel.getFirstName(), user.getFirstName());
        Assert.assertEquals(userModel.getLastName(), user.getLastName());
        Assert.assertEquals(userModel.getEmail(), user.getEmail());
    }

    @Test
    public void updatePassword_validData_shouldUpdatePassword() {
        UserChangePasswordBindingModel userModel = new UserChangePasswordBindingModel();
        userModel.setOldPassword("oldPass");
        userModel.setNewPassword("newPass");
        userModel.setConfirmNewPassword("newPass");

        User user = new User();
        userService.updatePassword(user, userModel);

        Mockito.verify(mockUserRepository).save(user);
        Assert.assertTrue(passwordEncoder.matches(userModel.getNewPassword(), user.getPassword()));
    }

    @Test
    public void suspendUser_validUser_shouldUpdateStatusToSuspended() {
        Long testId = 1L;
        User user = new User();
        Mockito.when(mockUserRepository.findById(testId)).thenReturn(Optional.of(user));

        userService.suspendUser(testId);

        Mockito.verify(mockUserRepository).save(user);
        Assert.assertEquals(UserStatus.SUSPENDED, user.getStatus());
    }

    @Test(expected = NotFoundException.class)
    public void suspendUser_nonexistentUser_shouldThrowNotFoundException() {
        Long testId = 1L;
        Mockito.when(mockUserRepository.findById(testId)).thenReturn(Optional.empty());

        userService.suspendUser(testId);
    }

    @Test
    public void activateUser_userHasLoggedInRecently_shouldUpdateStatusToActive() {
        Long testId = 1L;
        User user = new User();
        user.setLastLogin(LocalDateTime.now());
        Mockito.when(mockUserRepository.findById(testId)).thenReturn(Optional.of(user));

        userService.activateUser(testId);

        Mockito.verify(mockUserRepository).save(user);
        Assert.assertEquals(UserStatus.ACTIVE, user.getStatus());
    }

    @Test
    public void activateUser_userHasNotLoggedInRecently_shouldUpdateStatusToInactive() {
        Long testId = 1L;
        User user = new User();
        user.setLastLogin(LocalDateTime.now().minusMonths(2));
        Mockito.when(mockUserRepository.findById(testId)).thenReturn(Optional.of(user));

        userService.activateUser(testId);

        Mockito.verify(mockUserRepository).save(user);
        Assert.assertEquals(UserStatus.INACTIVE, user.getStatus());
    }

    @Test(expected = NotFoundException.class)
    public void activateUser_nonexistentUser_shouldThrowNotFoundException() {
        Long testId = 1L;
        Mockito.when(mockUserRepository.findById(testId)).thenReturn(Optional.empty());

        userService.suspendUser(testId);
    }

    @Test
    public void processUserLogin_always_shouldUpdateLastLoginAndStatus() {
        User user = new User();
        userService.processUserLogin(user);

        Mockito.verify(mockUserRepository).save(user);

        Assert.assertEquals(UserStatus.ACTIVE, user.getStatus());
        long diff = Math.abs(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - user.getLastLogin().toEpochSecond(ZoneOffset.UTC));
        Assert.assertTrue(diff < 1L);
    }

    @Test
    public void passwordsMatch_matchingPassowrds_shouldReturnTrue() {
        String rawPassword = "rawPass";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        boolean passwordsMatch = userService.passwordsMatch(rawPassword, encodedPassword);

        Assert.assertTrue(passwordsMatch);
    }

    @Test
    public void passwordsMatch_notMatchingPassowrds_shouldReturnTrue() {
        String rawPassword = "rawPass";
        String encodedPassword = passwordEncoder.encode("anotherPass");

        boolean passwordsMatch = userService.passwordsMatch(rawPassword, encodedPassword);

        Assert.assertFalse(passwordsMatch);
    }

    @Test
    public void updateRole_validUser_shouldUpdateRoleCorrectly() {
        Long testId = 1L;

        ChangeUserRoleBindingModel model = new ChangeUserRoleBindingModel();
        model.setUserId(testId);
        model.setRoleName(UserRole.ROLE_MODERATOR.getName());

        User user = new User();
        user.setAuthorities(Set.of());
        Mockito.when(mockUserRepository.findById(testId)).thenReturn(Optional.of(user));
        Mockito.when(mockRoleService.getRoleByName(UserRole.ROLE_MODERATOR.toString()))
                .thenReturn(new Role(UserRole.ROLE_MODERATOR.toString()));

        userService.updateRole(model);

        Mockito.verify(mockUserRepository).save(user);
        boolean containsNewRole = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(UserRole.ROLE_MODERATOR.toString()));
        Assert.assertTrue(containsNewRole);
    }

    @Test(expected = ForbiddenActionException.class)
    public void updateRole_userIsRootAdin_shouldThrowForbiddenActionException() {
        Long testId = 1L;

        ChangeUserRoleBindingModel model = new ChangeUserRoleBindingModel();
        model.setUserId(testId);
        model.setRoleName(UserRole.ROLE_MODERATOR.getName());

        User user = new User();
        user.setAuthorities(Set.of(new Role(UserRole.ROLE_ROOT_ADMIN.toString())));
        Mockito.when(mockUserRepository.findById(testId)).thenReturn(Optional.of(user));

        userService.updateRole(model);
    }

    @Test(expected = NotFoundException.class)
    public void updateRole_nonexistentUser_shouldThrowNotFoundException() {
        Long testId = 1L;

        ChangeUserRoleBindingModel model = new ChangeUserRoleBindingModel();
        model.setUserId(testId);
        model.setRoleName(UserRole.ROLE_MODERATOR.getName());

        Mockito.when(mockUserRepository.findById(testId)).thenReturn(Optional.empty());
        userService.updateRole(model);
    }

    @Test
    public void existsByEmail_existingEmail_shouldReturnTrue() {
        String testEmail = "test@test.bg";
        Mockito.when(mockUserRepository.existsByEmail(testEmail)).thenReturn(true);

        Assert.assertTrue(userService.existsByEmail(testEmail));
    }

    @Test
    public void existsByEmail_nonexistentEmail_shouldReturnFalse() {
        String testEmail = "test@test.bg";
        Mockito.when(mockUserRepository.existsByEmail(testEmail)).thenReturn(false);

        Assert.assertFalse(userService.existsByEmail(testEmail));
    }

    @Test
    public void getUsersPage_always_shouldReturnCorrectData() {
        Long testUsersCount = 5L;
        Pageable pageable = PageRequest.of(1, 20);
        User testUser1 = new User();
        testUser1.setAuthorities(Set.of(new Role(UserRole.ROLE_USER.toString())));
        User testUser2 = new User();
        testUser2.setAuthorities(Set.of(new Role(UserRole.ROLE_USER.toString())));
        User testUser3 = new User();
        testUser3.setAuthorities(Set.of(new Role(UserRole.ROLE_USER.toString())));

        List<User> users = List.of(testUser1, testUser2, testUser3);
        Page<User> usersPage = new PageImpl<>(users);

        Mockito.when(mockUserRepository.count()).thenReturn(testUsersCount);
        Mockito.when(mockUserRepository.findAll(pageable)).thenReturn(usersPage);

        UsersPageVO usersPageVO = userService.getUsersPage(pageable);

        Assert.assertEquals((long) testUsersCount, usersPageVO.getTotalItemsCount());
        Assert.assertEquals(users.size(), usersPageVO.getUsers().size());
    }

    @Test
    public void getUserDetails_validUser_shouldReturnCorrectUserDetailsVO() {
        Long testId = 1L;

        LocalDateTime lastLogin = LocalDateTime.now();

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Testov");
        user.setEmail("test@test.test");
        user.setLastLogin(lastLogin);
        user.setStatus(UserStatus.ACTIVE);
        user.setAuthorities(Set.of(new Role(UserRole.ROLE_USER.toString())));
        user.setImports(List.of());
        user.setExports(List.of());

        Mockito.when(mockUserRepository.findById(testId)).thenReturn(Optional.of(user));

        UserDetailsVO userDetails = userService.getUserDetails(testId);

        Assert.assertEquals(user.getFirstName(), userDetails.getFirstName());
        Assert.assertEquals(user.getLastName(), userDetails.getLastName());
        Assert.assertEquals(user.getEmail(), userDetails.getEmail());
        Assert.assertEquals(user.getLastLogin(), userDetails.getLastLogin());
        Assert.assertEquals(user.getStatus(), userDetails.getStatus());
        Assert.assertEquals(CustomStringUtils.getUserRole(user), userDetails.getRole());
    }

    @Test(expected = NotFoundException.class)
    public void getUserDetails_nonexistentUser_shouldThrowNotFoundException() {
        Long testId = 1L;
        Mockito.when(mockUserRepository.findById(testId)).thenReturn(Optional.empty());

        userService.getUserDetails(testId);
    }

    @Test
    public void updateUserStatus_always_shouldUpdateStatusesCorrectly() {
        User inactiveUser = new User();
        inactiveUser.setLastLogin(LocalDateTime.now().minusMonths(2));
        inactiveUser.setStatus(UserStatus.ACTIVE);

        User activeUser = new User();
        activeUser.setLastLogin(LocalDateTime.now().minusDays(1));
        activeUser.setStatus(UserStatus.ACTIVE);

        User inactiveUser2 = new User();
        inactiveUser2.setLastLogin(LocalDateTime.now().minusMonths(2));
        inactiveUser2.setStatus(UserStatus.INACTIVE);

        User suspendedUser = new User();
        suspendedUser.setLastLogin(LocalDateTime.now().minusDays(1));
        suspendedUser.setStatus(UserStatus.SUSPENDED);

        List<User> users = List.of(inactiveUser, activeUser, inactiveUser, suspendedUser);


        Mockito.when(mockUserRepository.findAll()).thenReturn(users);

        userService.updateUserStatus();

        Assert.assertEquals(UserStatus.INACTIVE, inactiveUser.getStatus());
        Assert.assertEquals(UserStatus.ACTIVE, activeUser.getStatus());
        Assert.assertEquals(UserStatus.INACTIVE, inactiveUser2.getStatus());
        Assert.assertEquals(UserStatus.SUSPENDED, suspendedUser.getStatus());
    }
}
