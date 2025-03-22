package org.example.service.impl;

import org.example.models.binding.IngredientAddBindingModel;
import org.example.models.entity.IngredientEntity;
import org.example.models.enums.IngredientType;
import org.example.repository.IngredientRepository;
import org.example.service.IngredientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
 private final IngredientRepository ingredientRepository;
 private final ModelMapper modelMapper;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, ModelMapper modelMapper) {
        this.ingredientRepository = ingredientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addIngredient(IngredientAddBindingModel ingredientAddBindingModel) {
        IngredientEntity ingredient = new IngredientEntity();
        ingredient.setName(ingredientAddBindingModel.getName());
        ingredient.setType(IngredientType.valueOf(ingredientAddBindingModel.getType()));

        ingredientRepository.save(ingredient);
    }

    public List<IngredientEntity> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    public List<IngredientEntity> findAllById(List<Long> ingredients) {
        return ingredientRepository.findAllById(ingredients);
    }

}
