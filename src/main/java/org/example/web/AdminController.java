package org.example.web;

import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/manage-roles")
    public String showUserManagementPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "manage-roles";
    }


    @PostMapping("/users/{username}/make-admin")
    public String makeAdmin(@PathVariable String username) {
        userService.assignAdminRole(username);
        return "redirect:/admin/users"; // Refresh the page
    }

}