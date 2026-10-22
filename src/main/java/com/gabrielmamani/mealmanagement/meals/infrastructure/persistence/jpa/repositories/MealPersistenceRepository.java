package com.gabrielmamani.mealmanagement.meals.infrastructure.persistence.jpa.repositories;

import com.gabrielmamani.mealmanagement.meals.domain.model.aggregates.Meal;
import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.MealType;
import com.gabrielmamani.mealmanagement.meals.infrastructure.persistence.jpa.entities.MealPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link MealPersistenceEntity}.
 */
public interface MealPersistenceRepository extends JpaRepository<MealPersistenceEntity, Long> {

    List<MealPersistenceEntity> findByAvailableTrue();
    List<MealPersistenceEntity> findByMealTypeAndAvailableTrue(MealType mealType);
    List<MealPersistenceEntity> findByProviderIdAndAvailableTrue(Long providerId);
    List<MealPersistenceEntity> findByProviderId(Long providerId);

}

