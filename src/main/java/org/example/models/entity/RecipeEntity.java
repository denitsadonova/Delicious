package org.example.models.entity;

import jakarta.persistence.*;
import org.example.models.enums.RecipeType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<IngredientEntity> ingredients;
    @Enumerated(EnumType.STRING)
    private RecipeType type;
    @Column(nullable = false)
    private Float preparationTime;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String preparationGuide;

    public RecipeEntity() {
        this.ingredients = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<IngredientEntity> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientEntity> ingredients) {
        this.ingredients = ingredients;
    }

    public RecipeType getType() {
        return type;
    }

    public void setType(RecipeType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
