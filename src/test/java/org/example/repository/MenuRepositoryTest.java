package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.models.entity.*;
import org.example.models.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;

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
        // Given
        RecipeEntity newRecipe = recipeRepository.findByName("Musaka");
        MenuEntity menu = new MenuEntity();
        menu.setUser(testUser);
        menu.setDayOfWeek(DayOfWeek.MONDAY);
        menu.setRecipes(List.of(newRecipe));
        menuRepository.save(menu);

        // When
        Optional<MenuEntity> result = menuRepository.findByUserAndDayOfWeek(testUser, DayOfWeek.MONDAY);

        // Then
        assertTrue(result.isPresent());
        assertEquals(DayOfWeek.MONDAY, result.get().getDayOfWeek());
    }

    @Test
    void findAllOrderByDayOfWeekAndRecipeType() {
        // Given
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

        // When
        List<MenuEntity> menus = menuRepository.findAllOrderByDayOfWeekAndRecipeType();

        // Then
        assertNotNull(menus);
        assertEquals(2, menus.size());
        assertTrue(menus.get(0).getDayOfWeek().compareTo(menus.get(1).getDayOfWeek()) < 0); // Check day order
    }

    @Test
    void countByUser() {
        // Given
        MenuEntity menu1 = new MenuEntity();
        menu1.setUser(testUser);
        menuRepository.save(menu1);

        MenuEntity menu2 = new MenuEntity();
        menu2.setUser(testUser);
        menuRepository.save(menu2);

        // When
        long count = menuRepository.countByUser(testUser);

        // Then
        assertEquals(2, count);
    }

    @Test
    void findMostPopularRecipe() {
        // Given: Mock data for popular recipes
        // You'd need to create some MenuEntity objects and associate them with recipes.
        // For this example, let's assume that the recipe entity is set up properly.
        // This is just a sample for you to follow. Adjust the actual recipe entity creation as per your actual models.

        MenuEntity menu1 = new MenuEntity();
        menu1.setUser(testUser);
        menu1.setRecipes(List.of(recipe));
        menu1.setDayOfWeek(DayOfWeek.MONDAY);
        // Add recipes to the menu (you can add a field for recipes in your MenuEntity)
        menuRepository.save(menu1);

        MenuEntity menu2 = new MenuEntity();
        menu2.setUser(testUser);
        menu2.setRecipes(List.of(recipe));
        menu2.setDayOfWeek(DayOfWeek.TUESDAY);
        // Add recipes to the menu
        menuRepository.save(menu2);

        List<Object[]> popularRecipes = menuRepository.findMostPopularRecipe();

        assertNotNull(popularRecipes);

        assertTrue((long) popularRecipes.get(0)[1] > 0);  // The count should be greater than zero for a valid recipe.
    }
}
