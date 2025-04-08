package org.example.repository;

import org.example.models.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
    IngredientEntity findByName(String name);
    Optional<IngredientEntity> findByNameIgnoreCase(String name);

}
