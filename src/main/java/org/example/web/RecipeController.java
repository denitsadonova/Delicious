package org.example.web;

import jakarta.validation.Valid;
import org.example.models.binding.RecipeAddBindingModel;
import org.example.service.IngredientService;
import org.example.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public RecipeController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/add/recipe")
    public String addRecipe(Model model){
        model.addAttribute("allIngredients", ingredientService.getAllIngredients());
        return "add-recipe";
    }

    @PostMapping("/add/recipe")
    public String addRecipeConfirm(@Valid @ModelAttribute("recipeAddBindingModel") RecipeAddBindingModel recipeAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeAddBindingModel", recipeAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeAddBindingModel", bindingResult);
            return "redirect:/add/recipe";
        }
        this.recipeService.addRecipe(recipeAddBindingModel);
        return "redirect:/success/add/recipe";
    }

@GetMapping("/success/add/recipe")
public String successAddRecipe() {
        return "success-add-recipe";
    }

    @GetMapping("/all/recipes")
    public String allRecipes(Model model){
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "all-recipes";
    }


    @ModelAttribute
    public RecipeAddBindingModel recipeAddBindingModel() {
        return  new RecipeAddBindingModel();}
}
