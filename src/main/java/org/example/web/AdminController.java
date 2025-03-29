package org.example.web;

import org.example.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
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

@GetMapping
@PreAuthorize("hasRole('ADMIN')")
public String showUserManagementPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }


    @PostMapping("/users/{username}/make-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String makeAdmin(@PathVariable String username) {
        userService.assignAdminRole(username);
        return "redirect:/"; // Refresh the page
    }

    @PostMapping("/updateRole/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUserRole(@PathVariable Long userId, String role) {
        userService.updateUserRole(userId, role);
        return "redirect:/";
    }


}