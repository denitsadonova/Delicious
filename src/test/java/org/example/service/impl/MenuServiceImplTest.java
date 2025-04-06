package org.example.service.impl;

import org.example.models.entity.MenuEntity;
import org.example.models.entity.RecipeEntity;
import org.example.models.entity.UserEntity;
import org.example.models.enums.DayOfWeek;
import org.example.repository.MenuRepository;
import org.example.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private MenuServiceImpl menuService;

    private UserEntity user;
    private RecipeEntity recipe;
    private MenuEntity menu;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setUsername("testUser");

        recipe = new RecipeEntity();
        recipe.setId(1L);
        recipe.setName("Test Recipe");

        menu = new MenuEntity();
        menu.setId(1L);
        menu.setUser(user);
        menu.setDayOfWeek(DayOfWeek.MONDAY);
        menu.setRecipes(new ArrayList<>());
    }

    @Test
    void createMenu_Success() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(menuRepository.findByUserAndDayOfWeek(user, DayOfWeek.MONDAY)).thenReturn(Optional.of(menu));

        menuService.createMenu(user, DayOfWeek.MONDAY, 1L);

        assertEquals(1, menu.getRecipes().size());
        verify(menuRepository).save(menu);
    }

    @Test
    void createMenu_NewMenu() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(menuRepository.findByUserAndDayOfWeek(user, DayOfWeek.MONDAY)).thenReturn(Optional.empty());

        ArgumentCaptor<MenuEntity> menuCaptor = ArgumentCaptor.forClass(MenuEntity.class);

        menuService.createMenu(user, DayOfWeek.MONDAY, 1L);

        verify(menuRepository).save(menuCaptor.capture());
        MenuEntity savedMenu = menuCaptor.getValue();

        assertNotNull(savedMenu.getRecipes());
        assertEquals(1, savedMenu.getRecipes().size());
    }

    @Test
    void getGroupedMenus_ReturnsMenus() {
        List<MenuEntity> menus = List.of(menu);
        when(menuRepository.findAllOrderByDayOfWeekAndRecipeType()).thenReturn(menus);

        List<MenuEntity> result = menuService.getGroupedMenus();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void removeRecipeFromMenu_Success() {
        RecipeEntity recipe = new RecipeEntity();
        recipe.setId(1L);
        menu.getRecipes().add(recipe);

        when(menuRepository.findByUserAndDayOfWeek(user, DayOfWeek.MONDAY)).thenReturn(Optional.of(menu));

        menuService.removeRecipeFromMenu(user, DayOfWeek.MONDAY, 1L);

        assertTrue(menu.getRecipes().isEmpty());
        verify(menuRepository).delete(menu);
    }

    @Test
    void removeRecipeFromMenu_NoMenuFound() {
        when(menuRepository.findByUserAndDayOfWeek(user, DayOfWeek.MONDAY)).thenReturn(Optional.empty());

        menuService.removeRecipeFromMenu(user, DayOfWeek.MONDAY, 1L);

        verify(menuRepository, never()).save(any(MenuEntity.class));
        verify(menuRepository, never()).delete(any(MenuEntity.class));
    }
}