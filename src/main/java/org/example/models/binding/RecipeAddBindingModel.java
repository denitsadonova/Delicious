package org.example.models.binding;

import jakarta.validation.constraints.*;

import java.util.List;

public class RecipeAddBindingModel {
    @NotBlank(message = "{errorRequiredField}")
    @Size(min = 3, max = 20, message = "The name of the recipe must be at least 3 characters!")
    private String name;
    @NotEmpty(message = "{errorRequiredField}")
    private List<Long> ingredientIds;

    @NotBlank(message = "{errorRequiredField}")
    private String type;
    @Positive(message = "Preparation time must be a positive number!")

    private Float preparationTime;
    @NotBlank(message = "{errorRequiredField}")

    private String preparationGuide;

    public RecipeAddBindingModel() {
            }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getIngredientIds() {
        return ingredientIds;
    }

    public void setIngredientIds(List<Long> ingredientIds) {
        this.ingredientIds = ingredientIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Float preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getPreparationGuide() {
        return preparationGuide;
    }

    public void setPreparationGuide(String preparationGuide) {
        this.preparationGuide = preparationGuide;
    }

}
