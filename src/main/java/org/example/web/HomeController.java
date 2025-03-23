package org.example.web;

import org.example.models.entity.RecipeEntity;
import org.example.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final RecipeService recipeService;

    public HomeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        RecipeEntity featuredRecipe = recipeService.getFeaturedRecipe();
        model.addAttribute("featuredRecipe", featuredRecipe);
        return "index";
    }
}
