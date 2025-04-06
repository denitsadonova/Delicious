package org.example.repository;

import org.example.models.entity.*;
import org.example.models.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;

    private UserEntity testUser;
    private RecipeEntity recipe;

    @BeforeEach
    void setUp() {
        RoleEntity role = new RoleEntity();
        role.setRole(UserRoleEnum.USER);
        testUser = new UserEntity();
        testUser.setUsername("testUser");
        testUser.setPassword("Secret");
        testUser.setEmail("test@abv.bg");
        testUser.setRoles(List.of(role));
        userRepository.save(testUser);
        IngredientEntity ingredient = new IngredientEntity();
        ingredient.setName("Potato");
        ingredient.setType(IngredientType.VEGETABLE);
        ingredient.setClassOfFood(ClassOfFood.CARBOHYDRATE);
        recipe = new RecipeEntity();
        recipe.setName("Musaka");
        recipe.setType(RecipeType.DINNER);
        recipe.setPreparationTime(2F);
        recipe.setPreparationGuide("Bake it.");
        recipe.setIngredients(List.of(ingredient));
        recipeRepository.save(recipe);
    }

    @Test
    void findByUserAndDayOfWeek() {
        RecipeEntity newRecipe = recipeRepository.findByName("Musaka");
        MenuEntity menu = new MenuEntity();
        menu.setUser(testUser);
        menu.setDayOfWeek(DayOfWeek.MONDAY);
        menu.setRecipes(List.of(newRecipe));
        menuRepository.save(menu);

        Optional<MenuEntity> result = menuRepository.findByUserAndDayOfWeek(testUser, DayOfWeek.MONDAY);

        assertTrue(result.isPresent());
        assertEquals(DayOfWeek.MONDAY, result.get().getDayOfWeek());
    }

    @Test
    void findAllOrderByDayOfWeekAndRecipeType() {
        RecipeEntity newRecipe = recipeRepository.findByName("Musaka");
        MenuEntity menu1 = new MenuEntity();
        menu1.setUser(testUser);
        menu1.setDayOfWeek(DayOfWeek.MONDAY);
        menu1.setId(1L);
        menu1.setRecipes(List.of(newRecipe));
        menuRepository.save(menu1);

        MenuEntity menu2 = new MenuEntity();
        menu2.setUser(testUser);
        menu2.setDayOfWeek(DayOfWeek.TUESDAY);
        menu2.setId(2L);
        menu2.setRecipes(List.of(newRecipe));
        menuRepository.save(menu2);

        List<MenuEntity> menus = menuRepository.findAllOrderByDayOfWeekAndRecipeType();

        assertNotNull(menus);
        assertEquals(2, menus.size());
        assertTrue(menus.get(0).getDayOfWeek().compareTo(menus.get(1).getDayOfWeek()) < 0); // Check day order
    }

    @Test
    void countByUser() {
        MenuEntity menu1 = new MenuEntity();
        menu1.setUser(testUser);
        menuRepository.save(menu1);

        MenuEntity menu2 = new MenuEntity();
        menu2.setUser(testUser);
        menuRepository.save(menu2);

        long count = menuRepository.countByUser(testUser);

        assertEquals(2, count);
    }

    @Test
    void findMostPopularRecipe() {

        MenuEntity menu1 = new MenuEntity();
        menu1.setUser(testUser);
        menu1.setRecipes(List.of(recipe));
        menu1.setDayOfWeek(DayOfWeek.MONDAY);
        menuRepository.save(menu1);

        MenuEntity menu2 = new MenuEntity();
        menu2.setUser(testUser);
        menu2.setRecipes(List.of(recipe));
        menu2.setDayOfWeek(DayOfWeek.TUESDAY);
        menuRepository.save(menu2);

        List<Object[]> popularRecipes = menuRepository.findMostPopularRecipe();

        assertNotNull(popularRecipes);

        assertTrue((long) popularRecipes.get(0)[1] > 0);
    }
}
