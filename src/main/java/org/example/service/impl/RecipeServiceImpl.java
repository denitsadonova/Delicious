package org.example.service.impl;

import org.example.models.entity.IngredientEntity;
import org.example.models.entity.RecipeEntity;
import org.example.models.binding.RecipeAddBindingModel;
import org.example.repository.IngredientRepository;
import org.example.repository.MenuRepository;
import org.example.repository.RecipeRepository;
import org.example.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final IngredientRepository ingredientRepository;

    private RecipeEntity featuredRecipe;

    public RecipeServiceImpl(RecipeRepository recipeRepository, MenuRepository menuRepository, ModelMapper modelMapper, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void addRecipe(RecipeAddBindingModel recipeAddBindingModel) {
        RecipeEntity recipe = modelMapper.map(recipeAddBindingModel, RecipeEntity.class);
        List<IngredientEntity> allIngredients = ingredientRepository.findAllById(recipeAddBindingModel.getIngredientIds());

        recipe.setIngredients(allIngredients);
        recipeRepository.save(recipe);
    }

    @Override
    public List<RecipeEntity> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public RecipeEntity getFeaturedRecipe() {
        return featuredRecipe;
    }

    @Scheduled(cron = "0 0 6 * * ?")
    public void updateFeaturedRecipe() {
        System.out.println("Selecting the most popular recipe for the homepage...");

        List<Object[]> result = menuRepository.findMostPopularRecipe();
        if (!result.isEmpty()) {
            Long recipeId = (Long) result.get(0)[0];
            featuredRecipe = recipeRepository.findById(recipeId).orElse(null);
            System.out.println("Featured Recipe: " + (featuredRecipe != null ? featuredRecipe.getName() : "None"));
        }
    }
}
