package com.devscracks.mealmanagement.meals.domain.model.queries;

import com.devscracks.mealmanagement.meals.domain.model.valueobjects.MealType;

/**
 * Query to list all available meals of a given {@link MealType}.
 */
public record GetMealsByMealTypeQuery(MealType mealType) {
}
