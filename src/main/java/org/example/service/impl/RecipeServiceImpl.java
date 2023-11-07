package org.example.service.impl;

import org.example.models.entity.IngredientEntity;
import org.example.models.entity.RecipeEntity;
import org.example.models.binding.RecipeAddBindingModel;
import org.example.repository.IngredientRepository;
import org.example.repository.RecipeRepository;
import org.example.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final IngredientRepository ingredientRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository, ModelMapper modelMapper, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void addRecipe(RecipeAddBindingModel recipeAddBindingModel) {
        RecipeEntity recipe = modelMapper.map(recipeAddBindingModel, RecipeEntity.class);
        List<String> ingredients = Arrays.stream(recipeAddBindingModel.getIngredients().split(", ")).toList();
       List<IngredientEntity> allIngredients = new ArrayList<>();
        for (String ingredient : ingredients) {
            IngredientEntity ingredientEntity = ingredientRepository.findByName(ingredient);
            allIngredients.add(ingredientEntity);
        }
        recipe.setIngredients(allIngredients);
        recipeRepository.save(recipe);

    }
}
