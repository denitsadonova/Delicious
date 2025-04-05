package org.example.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.models.binding.RecipeAddBindingModel;
import org.example.models.entity.IngredientEntity;
import org.example.models.entity.RecipeEntity;
import org.example.repository.IngredientRepository;
import org.example.repository.MenuRepository;
import org.example.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private RecipeEntity testRecipe;
    private IngredientEntity ingredient;

    @BeforeEach
    void setUp() {
        testRecipe = new RecipeEntity();
        testRecipe.setId(1L);
        testRecipe.setName("Test Recipe");

        ingredient = new IngredientEntity();
        ingredient.setId(1L);
        ingredient.setName("Salt");
    }

    @Test
    void addRecipe_Success() {
        RecipeAddBindingModel recipeModel = new RecipeAddBindingModel();
        recipeModel.setName("New Recipe");
        recipeModel.setIngredientIds(List.of(1L));

        when(modelMapper.map(recipeModel, RecipeEntity.class)).thenReturn(testRecipe);
        when(ingredientRepository.findAllById(recipeModel.getIngredientIds())).thenReturn(List.of(ingredient));

        recipeService.addRecipe(recipeModel);

        verify(recipeRepository).save(any(RecipeEntity.class));
    }

    @Test
    void getAllRecipes_ReturnsRecipes() {
        when(recipeRepository.findAll()).thenReturn(List.of(testRecipe));

        List<RecipeEntity> recipes = recipeService.getAllRecipes();
        assertFalse(recipes.isEmpty());
        assertEquals(1, recipes.size());
    }

    @Test
    void getFeaturedRecipe_ReturnsCurrentFeaturedRecipe() {
        assertNull(recipeService.getFeaturedRecipe());
    }

    @Test
    void updateFeaturedRecipe_Success() {
        List<Object[]> mockResult = new ArrayList<>();
        mockResult.add(new Object[]{1L});         when(menuRepository.findMostPopularRecipe()).thenReturn(mockResult);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(testRecipe));

        recipeService.updateFeaturedRecipe();

        assertEquals("Test Recipe", recipeService.getFeaturedRecipe().getName());
    }

    @Test
    void updateFeaturedRecipe_NoPopularRecipe_FeaturedRemainsNull() {
        when(menuRepository.findMostPopularRecipe()).thenReturn(List.of());

        recipeService.updateFeaturedRecipe();

        assertNull(recipeService.getFeaturedRecipe());
    }
}