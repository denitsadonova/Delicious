package org.example.service.impl;

import org.example.models.entity.MenuEntity;
import org.example.models.entity.RecipeEntity;
import org.example.models.entity.UserEntity;
import org.example.models.enums.DayOfWeek;
import org.example.repository.MenuRepository;
import org.example.repository.RecipeRepository;
import org.example.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final RecipeRepository recipeRepository;

    public MenuServiceImpl(MenuRepository menuRepository, RecipeRepository recipeRepository) {
        this.menuRepository = menuRepository;
        this.recipeRepository = recipeRepository;
    }

    public void createMenu(UserEntity user, DayOfWeek dayOfWeek, Long recipeId) {

        RecipeEntity recipe = recipeRepository.findById(recipeId).orElseThrow();

        MenuEntity menu = menuRepository.findByUserAndDayOfWeek(user, dayOfWeek)
                .orElse(new MenuEntity());

        menu.setDayOfWeek(dayOfWeek);
        menu.setUser(user);
        List<RecipeEntity> existingRecipes = menu.getRecipes();
        existingRecipes.add(recipe);
        menu.setRecipes(existingRecipes);
        menuRepository.save(menu);
    }

    public List<MenuEntity> getGroupedMenus() {
        return menuRepository.findAllOrderByDayOfWeekAndRecipeType();
    }

    @Override
    public void removeRecipeFromMenu(UserEntity user, DayOfWeek dayOfWeek, Long recipeId) {
        Optional<MenuEntity> menuOpt = menuRepository.findByUserAndDayOfWeek(user, dayOfWeek);

        if (menuOpt.isPresent()) {
            MenuEntity menu = menuOpt.get();
            menu.getRecipes().removeIf(recipe -> recipe.getId() == (recipeId));

            if (menu.getRecipes().isEmpty()) {
                menuRepository.delete(menu);
            } else {
                menuRepository.save(menu);
            }
        }
    }
}
