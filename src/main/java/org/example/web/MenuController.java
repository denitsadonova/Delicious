package org.example.web;

import org.example.models.entity.MenuEntity;
import org.example.models.entity.RecipeEntity;
import org.example.models.entity.UserEntity;
import org.example.models.enums.DayOfWeek;
import org.example.models.enums.RecipeType;
import org.example.repository.MenuRepository;
import org.example.repository.RecipeRepository;
import org.example.repository.UserRepository;
import org.example.service.MenuService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class MenuController {
    private final MenuService menuService;
    private final MenuRepository menuRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public MenuController(MenuService menuService, MenuRepository menuRepository, RecipeRepository recipeRepository, UserRepository userRepository) {
        this.menuService = menuService;
        this.menuRepository = menuRepository;
        this.recipeRepository = recipeRepository;
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