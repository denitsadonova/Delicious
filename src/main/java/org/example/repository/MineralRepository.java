package org.example.repository;

import org.example.models.entity.MineralEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MineralRepository extends JpaRepository<MineralEntity, Long> {
}
