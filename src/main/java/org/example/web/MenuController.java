package org.example.web;

import org.example.models.entity.MenuEntity;
import org.example.models.entity.UserEntity;
import org.example.models.enums.DayOfWeek;
import org.example.repository.UserRepository;
import org.example.service.MenuService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MenuController {
    private final MenuService menuService;
    private final UserRepository userRepository;

    public MenuController(MenuService menuService, UserRepository userRepository) {
        this.menuService = menuService;
        this.userRepository = userRepository;
    }

    @GetMapping("/menu")
    public String menuPage(Model model) {
        List<MenuEntity> groupedMenus = menuService.getGroupedMenus();
        model.addAttribute("menus", groupedMenus);
        return "menu";
    }

    @PostMapping("/menus/recipes/{recipeId}/add")
    public String addRecipeToMenu(@PathVariable Long recipeId, @RequestParam DayOfWeek dayOfWeek) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        menuService.createMenu(user, dayOfWeek, recipeId);

        return "success-add-menu";
    }

}