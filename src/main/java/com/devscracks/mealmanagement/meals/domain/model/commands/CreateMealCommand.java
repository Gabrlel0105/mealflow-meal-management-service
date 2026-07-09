package com.devscracks.mealmanagement.meals.domain.model.commands;

import com.devscracks.mealmanagement.meals.domain.model.valueobjects.MealType;

import java.math.BigDecimal;

/**
 * Command to create a new meal owned by a provider.
 */
public record CreateMealCommand(
        Long providerId,
        String name,
        String description,
        BigDecimal price,
        Integer calories,
        Double protein,
        Double carbs,
        Double fat,
        MealType mealType,
        String imageUrl) {
}
