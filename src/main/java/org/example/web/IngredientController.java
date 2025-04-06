package org.example.web;

import jakarta.validation.Valid;
import org.example.models.binding.IngredientAddBindingModel;
import org.example.models.entity.IngredientEntity;
import org.example.repository.IngredientRepository;
import org.example.service.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class IngredientController {
    private final IngredientService ingredientService;
    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientService ingredientService, IngredientRepository ingredientRepository) {
        this.ingredientService = ingredientService;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("add/ingredient")
    public String addIngredient() {
        return "add-ingredient";
    }

    @PostMapping("/add/ingredient")
    public String addIngredientConfirm(@Valid IngredientAddBindingModel ingredientAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("ingredientAddBindingModel", ingredientAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ingredientAddBindingModel", bindingResult);
            return "redirect:add/ingredient";
        }
        List<IngredientEntity> allIngredients = ingredientRepository.findAll();
        model.addAllAttributes(allIngredients);
        ingredientService.addIngredient(ingredientAddBindingModel);

        return "redirect:/";
    }


    @ModelAttribute
    public IngredientAddBindingModel ingredientAddBindingModel() {
        return new IngredientAddBindingModel();
    }

}
