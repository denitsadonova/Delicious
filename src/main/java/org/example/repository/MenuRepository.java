package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.models.entity.MenuEntity;
import org.example.models.entity.UserEntity;
import org.example.models.enums.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    Optional<MenuEntity> findByUserAndDayOfWeek(UserEntity user, DayOfWeek dayOfWeek);

    @Transactional
    @Query("SELECT m FROM MenuEntity m JOIN FETCH m.recipes r ORDER BY m.dayOfWeek, r.type")
    List<MenuEntity> findAllOrderByDayOfWeekAndRecipeType();

    long countByUser(UserEntity user);

    @Transactional
    @Query("SELECT r.id, COUNT(m) AS count FROM MenuEntity m JOIN m.recipes r GROUP BY r.id ORDER BY count DESC")
    List<Object[]> findMostPopularRecipe();
}
