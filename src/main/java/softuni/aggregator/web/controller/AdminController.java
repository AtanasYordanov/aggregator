package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.domain.enums.UserRole;
import softuni.aggregator.domain.model.binding.ChangeUserRoleBindingModel;
import softuni.aggregator.domain.model.vo.UserDetailsVO;
import softuni.aggregator.domain.model.vo.UserListVO;
import softuni.aggregator.domain.model.vo.page.UsersPageVO;
import softuni.aggregator.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ModelAndView getAllUsers(ModelAndView model) {
        model.setViewName("users");
        return model;
    }

    @GetMapping(value = "/users/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsersPageVO> getUsersPage(Pageable pageable) {
        List<UserListVO> users = userService.getUsersPage(pageable);
        long usersCount = userService.getTotalUsersCount();

        UsersPageVO usersPageVO = new UsersPageVO();
        usersPageVO.setUsers(users);
        usersPageVO.setTotalItemsCount(usersCount);

        return new ResponseEntity<>(usersPageVO, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ModelAndView getUserDetails(ModelAndView model, @PathVariable Long id) {
        UserDetailsVO user = userService.getUserDetails(id);
        model.addObject("user", user);
        model.setViewName("user-details");
        return model;
    }

    @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getModifiableRoles() {
        List<String> roles = Arrays.stream(UserRole.values())
                .filter(role -> !role.equals(UserRole.ROLE_ROOT_ADMIN))
                .map(UserRole::getName)
                .collect(Collectors.toList());

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping(value = "/roles/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeUserRole(@RequestBody ChangeUserRoleBindingModel bindingModel) {
        userService.updateRole(bindingModel);
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }
}
