package org.example.models.binding;

import jakarta.validation.constraints.*;

public class RecipeAddBindingModel {
    @NotBlank
    @Size(min = 3, max = 20, message = "The name of the recipe must be at least 3 characters!")
    private String name;
    @NotBlank(message = "You should add ingredients!")
    private String ingredients;

    @NotBlank(message = "You should select type!")
    private String type;
    @Positive(message = "Preparation time must be a positive number!")

    private Float preparationTime;
    @NotBlank(message = "You should add a guide for preparation!")

    private String preparationGuide;

    public RecipeAddBindingModel() {
            }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
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
