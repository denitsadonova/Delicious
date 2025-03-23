package org.example.service;

import org.example.models.entity.MenuEntity;
import org.example.models.entity.RecipeEntity;
import org.example.models.entity.UserEntity;
import org.example.models.enums.DayOfWeek;
import org.example.models.enums.RecipeType;

import java.util.List;
import java.util.Map;

public interface MenuService {
    public void createMenu(UserEntity user, DayOfWeek dayOfWeek, Long recipeId);
    public List<MenuEntity> getGroupedMenus();

    void removeRecipeFromMenu(UserEntity user, DayOfWeek dayOfWeek, Long recipeId);
}
