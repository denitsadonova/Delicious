package org.example.models.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "health")
public class HealthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<VitaminEntity> vitamins;
    @OneToMany
    private List<MineralEntity> minerals;

    public HealthEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<VitaminEntity> getVitamins() {
        return vitamins;
    }

    public void setVitamins(List<VitaminEntity> vitamins) {
        this.vitamins = vitamins;
    }

    public List<MineralEntity> getMinerals() {
        return minerals;
    }

    public void setMinerals(List<MineralEntity> minerals) {
        this.minerals = minerals;
    }
}
