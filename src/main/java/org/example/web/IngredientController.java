package org.example.web;

import jakarta.validation.Valid;
import org.example.models.binding.IngredientAddBindingModel;
import org.example.service.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("add/ingredient")
    public String addIngredient() {
        return "add-ingredient";
    }
    @PostMapping("/add/ingredient")
    public String addIngredientConfirm(@Valid IngredientAddBindingModel ingredientAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("ingredientAddBindingModel", ingredientAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ingredientAddBindingModel", bindingResult);
            return "redirect:add/ingredient";
        }

        ingredientService.addIngredient(ingredientAddBindingModel);

        return "redirect:/";
    }



    @ModelAttribute
    public IngredientAddBindingModel ingredientAddBindingModel(){
        return  new IngredientAddBindingModel();
    }

}
