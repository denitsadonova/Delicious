package org.example.models.binding;

import jakarta.validation.constraints.NotBlank;

public class IngredientAddBindingModel {
    @NotBlank(message = "{errorRequiredField}")
    private String name;
    @NotBlank(message = "{errorRequiredField}")
    private String type;

    public IngredientAddBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
