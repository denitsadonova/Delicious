package org.example.web;

import org.example.models.binding.RecipeAddBindingModel;
import org.example.service.IngredientService;
import org.example.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private IngredientService ingredientService;

    @Test
    void testGetAddRecipe() throws Exception {
        when(ingredientService.getAllIngredients()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/add/recipe").with(csrf()).with(user("testUser").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allIngredients"))
                .andExpect(view().name("add-recipe"));
    }

    @Test
    void testPostAddRecipe_ValidationError() throws Exception {
        mockMvc.perform(post("/add/recipe")
                        .with(csrf()).with(user("testUser").roles("USER"))
                        .param("name", "")  // trigger validation error
                        .param("ingredients", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/add/recipe"));
    }

    @Test
    void testGetSuccessAddRecipe() throws Exception {
        mockMvc.perform(get("/success/add/recipe").with(csrf()).with(user("testUser").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("success-add-recipe"));
    }

    @Test
    void testGetAllRecipes() throws Exception {
        when(recipeService.getAllRecipes()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/all/recipes").with(csrf()).with(user("testUser").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipes"))
                .andExpect(view().name("all-recipes"));
    }
}
