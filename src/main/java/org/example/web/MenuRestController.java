package org.example.web;

import org.example.models.entity.MenuEntity;
import org.example.models.entity.UserEntity;
import org.example.models.enums.DayOfWeek;
import org.example.repository.UserRepository;
import org.example.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuRestController {
    private final MenuService menuService;
    private final UserRepository userRepository;

    public MenuRestController(MenuService menuService, UserRepository userRepository) {
        this.menuService = menuService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<MenuEntity>> getMenus() {
        List<MenuEntity> menus = menuService.getGroupedMenus();
        return ResponseEntity.ok(menus);
    }

    @PostMapping("/recipes/{recipeId}/add")
    public ResponseEntity<String> addRecipeToMenu(@PathVariable Long recipeId, @RequestParam DayOfWeek dayOfWeek) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        menuService.createMenu(user, dayOfWeek, recipeId);

        return ResponseEntity.ok("Recipe added to menu successfully.");
    }
}