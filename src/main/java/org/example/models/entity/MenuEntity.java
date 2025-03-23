package org.example.models.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.example.models.enums.DayOfWeek;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menus")
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<RecipeEntity> recipes;
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    @OneToOne
    private UserEntity user;

    public MenuEntity() {
        this.recipes = new ArrayList<>();
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RecipeEntity> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeEntity> recipes) {
        this.recipes = recipes;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

}
