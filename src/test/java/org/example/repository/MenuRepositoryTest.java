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

}
