package org.example.repository;

import org.example.models.entity.VitaminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VitaminRepository extends JpaRepository<VitaminEntity, Long> {
    Optional<VitaminEntity> findByNameIgnoreCase(String name);
}
