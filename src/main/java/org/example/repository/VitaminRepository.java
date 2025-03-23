package org.example.repository;

import org.example.models.entity.VitaminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VitaminRepository extends JpaRepository<VitaminEntity, Long> {
}
