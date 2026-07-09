package com.devscracks.mealmanagement.meals.infrastructure.persistence.jpa.repositories;

import com.devscracks.mealmanagement.meals.domain.model.aggregates.Meal;
import com.devscracks.mealmanagement.meals.domain.model.valueobjects.MealType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the {@link Meal} aggregate.
 */
public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findByAvailableTrue();

    List<Meal> findByMealTypeAndAvailableTrue(MealType mealType);

    List<Meal> findByProviderIdAndAvailableTrue(Long providerId);

    List<Meal> findByProviderId(Long providerId);
}
