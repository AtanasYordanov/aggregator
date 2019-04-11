package softuni.aggregator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.aggregator.domain.model.binding.UserRegisterBindingModel;
import softuni.aggregator.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
            bindingResult.addError(new FieldError("bindingModel", "password", "Passwords do not match"));
            bindingResult.addError(new FieldError("bindingModel", "confirmPassword", "Passwords do not match"));
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
}
