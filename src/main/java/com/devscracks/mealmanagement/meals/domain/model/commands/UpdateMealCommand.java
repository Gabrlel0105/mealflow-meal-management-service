package com.devscracks.mealmanagement.meals.domain.model.commands;

import com.devscracks.mealmanagement.meals.domain.model.valueobjects.MealType;

import java.math.BigDecimal;

/**
 * Command to update an existing meal. Only the provider that created the meal may perform this operation.
 */
public record UpdateMealCommand(
        Long mealId,
        Long requesterId,
        String name,
        String description,
        BigDecimal price,
        Integer calories,
        Double protein,
        Double carbs,
        Double fat,
        MealType mealType,
        String imageUrl,
        Boolean available) {
}
