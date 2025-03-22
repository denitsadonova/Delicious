package org.example.service;

import org.example.models.binding.RecipeAddBindingModel;
import org.example.models.entity.RecipeEntity;

import java.util.List;

public interface RecipeService {
    RecipeEntity addRecipe(RecipeAddBindingModel recipeAddBindingModel);
    List<RecipeEntity> getAllRecipes();
}
