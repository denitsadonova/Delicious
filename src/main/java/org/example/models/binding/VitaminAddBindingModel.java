package org.example.models.binding;

import jakarta.validation.constraints.NotBlank;

public class VitaminAddBindingModel {
    @NotBlank(message = "{errorRequiredField}")
    private String name;
    @NotBlank(message = "{errorRequiredField}")
    private String description;

    public VitaminAddBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
