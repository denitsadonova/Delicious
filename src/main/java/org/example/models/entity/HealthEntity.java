package org.example.models.entity;

import jakarta.persistence.*;
import org.example.models.enums.Minerals;
import org.example.models.enums.Vitamins;

import java.util.List;

@Entity
@Table(name = "health")
public class HealthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private List<Vitamins> vitamins;
    @Enumerated(EnumType.STRING)
    private List<Minerals> minerals;


}
