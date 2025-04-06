package org.example.service;

import org.example.models.entity.MenuEntity;
import org.example.models.entity.UserEntity;
import org.example.models.enums.DayOfWeek;

import java.util.List;

public interface MenuService {
     void createMenu(UserEntity user, DayOfWeek dayOfWeek, Long recipeId);

     List<MenuEntity> getGroupedMenus();

    void removeRecipeFromMenu(UserEntity user, DayOfWeek dayOfWeek, Long recipeId);
}
