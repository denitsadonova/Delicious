package org.example.service;

import org.example.models.binding.RecipeAddBindingModel;
import org.example.models.entity.RecipeEntity;

import java.util.List;

public interface RecipeService {
    void addRecipe(RecipeAddBindingModel recipeAddBindingModel);
    List<RecipeEntity> getAllRecipes();

    RecipeEntity getFeaturedRecipe();
}
