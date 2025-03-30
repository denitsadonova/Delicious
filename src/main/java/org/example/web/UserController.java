package org.example.web;


import jakarta.validation.Valid;
import org.example.models.binding.UserRegisterBindingModel;
import org.example.models.binding.UserUpdateBindingModel;
import org.example.models.entity.UserEntity;
import org.example.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";}
    @GetMapping
    public String user(Model model, Principal principal) {
        UserEntity user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("/register")
    public String doRegister(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:register";
        }

        userService.registerUser(userRegisterBindingModel);
        return "redirect:login";

    }

        @PostMapping("/updateProfile")
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
        public String updateProfile(UserUpdateBindingModel request, Authentication authentication) {
            userService.updateUserProfile(authentication.getName(), request);
            return "redirect:/";
        }


    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel() {
        return  new UserRegisterBindingModel();
    }

}
