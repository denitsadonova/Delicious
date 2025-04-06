package org.example.service;

import org.example.models.binding.IngredientAddBindingModel;
import org.example.models.entity.IngredientEntity;

import java.util.List;

public interface IngredientService {
    void addIngredient(IngredientAddBindingModel ingredientAddBindingModel);

    List<IngredientEntity> getAllIngredients();

    List<IngredientEntity> findAllById(List<Long> ingredients);
}
