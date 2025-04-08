package org.example.repository;

import org.example.models.entity.MineralEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MineralRepository extends JpaRepository<MineralEntity, Long> {
    Optional<MineralEntity> findByNameIgnoreCase(String name);
}
