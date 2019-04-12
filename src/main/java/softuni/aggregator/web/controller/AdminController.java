package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.domain.model.binding.ChangeUserRoleBindingModel;
import softuni.aggregator.domain.model.vo.UserDetailsVO;
import softuni.aggregator.domain.model.vo.page.ExportsPageVO;
import softuni.aggregator.domain.model.vo.page.ImportsPageVO;
import softuni.aggregator.domain.model.vo.page.UsersPageVO;
import softuni.aggregator.service.ExportService;
import softuni.aggregator.service.ImportService;
import softuni.aggregator.service.RoleService;
import softuni.aggregator.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final ExportService exportService;
    private final ImportService importService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService,
                           ExportService exportService, ImportService importService) {
        this.userService = userService;
        this.roleService = roleService;
        this.exportService = exportService;
        this.importService = importService;
    }

    @GetMapping("/users")
    public ModelAndView getAllUsers(ModelAndView model) {
        model.setViewName("users");
        return model;
    }

    @GetMapping(value = "/users/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsersPageVO> getUsersPage(Pageable pageable) {
        UsersPageVO usersPageVO = userService.getUsersPage(pageable);
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
        List<String> roles = roleService.getModifiableRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping(value = "/roles/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeUserRole(@RequestBody ChangeUserRoleBindingModel bindingModel) {
        userService.updateRole(bindingModel);
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping("/exports")
    public ModelAndView allExports(ModelAndView model) {
        model.setViewName("exports");
        return model;
    }

    @GetMapping(value = "/exports/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExportsPageVO> getAllExports(Pageable pageable) {
        ExportsPageVO exportsPageVO = exportService.getAllExportsPage(pageable);
        return new ResponseEntity<>(exportsPageVO, HttpStatus.OK);
    }

    @GetMapping("/imports")
    public ModelAndView allImports(ModelAndView model) {
        model.setViewName("imports");
        return model;
    }

    @GetMapping("/imports/page")
    public ResponseEntity<ImportsPageVO> getAllImports(Pageable pageable) {
        ImportsPageVO importsPageVO = importService.getAllImportsPage(pageable);
        return new ResponseEntity<>(importsPageVO, HttpStatus.OK);
    }

    @PutMapping(value = "/suspend/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> suspendUser(@PathVariable Long userId) {
        userService.suspendUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/activate/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activateUser(@PathVariable Long userId) {
        userService.activateUser(userId);
        return ResponseEntity.ok().build();
    }
}
