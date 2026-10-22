package com.gabrielmamani.mealmanagement.meals.domain.repositories;

import com.gabrielmamani.mealmanagement.meals.domain.model.aggregates.Meal;
import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.MealType;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for persisting and retrieving {@link Meal} aggregates.
 */
public interface MealRepository {
    Optional<Meal> findById(Long id);

    List<Meal> findAll();

    Meal save(Meal meal);

    boolean existsById(Long id);

    List<Meal> findByAvailableTrue();

    List<Meal> findByMealTypeAndAvailableTrue(MealType mealType);

    List<Meal> findByProviderIdAndAvailableTrue(Long providerId);

    List<Meal> findByProviderId(Long providerId);
    void deleteById(Long id);
}

