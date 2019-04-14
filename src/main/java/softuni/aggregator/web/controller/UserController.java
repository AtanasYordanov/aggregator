package softuni.aggregator.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.constants.ErrorMessages;
import softuni.aggregator.domain.entities.User;
import softuni.aggregator.domain.model.binding.UserChangePasswordBindingModel;
import softuni.aggregator.domain.model.binding.UserEditProfileBindingModel;
import softuni.aggregator.domain.model.binding.UserRegisterBindingModel;
import softuni.aggregator.domain.model.vo.UserDetailsVO;
import softuni.aggregator.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping
public class UserController {

    private static final String PASSWORD_FIELD = "password";
    private static final String CONFIRM_PASSWORD_FIELD = "confirmPassword";
    private static final String OLD_PASSWORD_FIELD = "oldPassword";
    private static final String NEW_PASSWORD_FIELD = "newPassword";
    private static final String CONFIRM_NEW_PASSWORD_FIELD = "confirmNewPassword";

    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView model) {
        model.setViewName("login");
        return model;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView model,
                                 @ModelAttribute(name = "bindingModel") UserRegisterBindingModel bindingModel) {
        model.addObject("bindingModel", bindingModel);
        model.setViewName("register");
        return model;
    }

    @PostMapping("/register")
    public ModelAndView processRegister(
            @Valid @ModelAttribute(name = "bindingModel") UserRegisterBindingModel bindingModel,
            BindingResult bindingResult, ModelAndView model) {

        if (bindingModel.getPassword() != null && !bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
            bindingResult.addError(new FieldError("bindingModel", PASSWORD_FIELD, ErrorMessages.PASSWORD_DONT_MATCH));
            bindingResult.addError(new FieldError("bindingModel", CONFIRM_PASSWORD_FIELD, ErrorMessages.PASSWORD_DONT_MATCH));
        }

        if (bindingResult.hasErrors()) {
            model.addObject("bindingModel", bindingModel);
            model.setViewName("register");
            return model;
        }

        userService.registerUser(bindingModel);
        model.setViewName("redirect:/login");
        return model;
    }

    @GetMapping("/profile")
    public ModelAndView profile(@AuthenticationPrincipal User loggedUser, ModelAndView model) {
        UserDetailsVO user = userService.getUserDetails(loggedUser.getId());
        model.addObject("user", user);
        model.setViewName("profile");
        return model;
    }

    @GetMapping("/profile/edit")
    public ModelAndView editProfile(@AuthenticationPrincipal User loggedUser, ModelAndView model,
                                    @ModelAttribute(name = "bindingModel") UserRegisterBindingModel bindingModel) {
        UserDetailsVO user = userService.getUserDetails(loggedUser.getId());
        mapper.map(user, bindingModel);
        model.addObject("bindingModel", bindingModel);
        model.setViewName("edit-profile");
        return model;
    }

    @PutMapping("/profile/edit")
    public ModelAndView processEditProfile(@AuthenticationPrincipal User loggedUser,
                                           @Valid @ModelAttribute(name = "bindingModel") UserEditProfileBindingModel bindingModel,
                                           BindingResult bindingResult, ModelAndView model) {

        if (bindingResult.hasErrors()) {
            model.addObject("bindingModel", bindingModel);
            model.setViewName("edit-profile");
            return model;
        }

        userService.updateProfile(loggedUser, bindingModel);
        model.setViewName("redirect:/profile");
        return model;
    }

    @GetMapping("/profile/password")
    public ModelAndView editProfile(@ModelAttribute(name = "bindingModel") UserChangePasswordBindingModel bindingModel,
                                    ModelAndView model) {
        model.addObject("bindingModel", bindingModel);
        model.setViewName("change-password");
        return model;
    }

    @PutMapping("/profile/password")
    public ModelAndView processEditProfile(@AuthenticationPrincipal User loggedUser,
                                           @ModelAttribute(name = "bindingModel") UserChangePasswordBindingModel bindingModel,
                                           BindingResult bindingResult, ModelAndView model) {

        if (bindingModel.getNewPassword() != null && !bindingModel.getNewPassword().equals(bindingModel.getConfirmNewPassword())) {
            bindingResult.addError(new FieldError("bindingModel", NEW_PASSWORD_FIELD, ErrorMessages.PASSWORD_DONT_MATCH));
            bindingResult.addError(new FieldError("bindingModel", CONFIRM_NEW_PASSWORD_FIELD, ErrorMessages.PASSWORD_DONT_MATCH));
        }

        if (!userService.passwordsMatch(bindingModel.getOldPassword(), loggedUser.getPassword())) {
            bindingResult.addError(new FieldError("bindingModel", OLD_PASSWORD_FIELD, ErrorMessages.WRONG_PASSWORD));
        }

        if (bindingResult.hasErrors()) {
            model.addObject("bindingModel", bindingModel);
            model.setViewName("change-password");
            return model;
        }

        userService.updatePassword(loggedUser, bindingModel);

        model.setViewName("redirect:/profile");
        return model;
    }
}
