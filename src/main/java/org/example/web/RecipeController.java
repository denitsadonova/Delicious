package org.example.web;

import jakarta.validation.Valid;
import org.example.models.binding.RecipeAddBindingModel;
import org.example.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/add/recipe")
    public String addRecipe(){
        return "add-recipe";
    }
    @PostMapping("/add/recipe")
    public String addRecipeConfirm(@Valid RecipeAddBindingModel recipeAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeAddBindingModel", recipeAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeAddBindingModel", bindingResult);
            return "redirect:add/recipe";
        }

        this.recipeService.addRecipe(recipeAddBindingModel);



        return "redirect:/";
    }





    @ModelAttribute
    public RecipeAddBindingModel recipeAddBindingModel() {
        return  new RecipeAddBindingModel();}
}
