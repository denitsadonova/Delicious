package org.example.models.entity;

import jakarta.persistence.*;
import org.example.models.enums.ClassOfFood;
import org.example.models.enums.IngredientType;


@Entity
@Table(name = "ingredients")
public class IngredientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private IngredientType type;
    @Enumerated(EnumType.STRING)
    private ClassOfFood classOfFood;


    public IngredientEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IngredientType getType() {
        return type;
    }

    public void setType(IngredientType type) {
        this.type = type;
    }

    public ClassOfFood getClassOfFood() {
        return classOfFood;
    }

    public void setClassOfFood(ClassOfFood classOfFood) {
        this.classOfFood = classOfFood;
    }
}
