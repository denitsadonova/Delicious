package org.example.service.impl;

import org.example.models.entity.MenuEntity;
import org.example.models.entity.RecipeEntity;
import org.example.models.entity.UserEntity;
import org.example.models.enums.DayOfWeek;
import org.example.models.enums.RecipeType;
import org.example.repository.MenuRepository;
import org.example.repository.RecipeRepository;
import org.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RecipeRepository recipeRepository;

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

}
