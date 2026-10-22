package com.gabrielmamani.mealmanagement.meals.domain.model.queries;

import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.MealType;

/**
 * Query to retrieve available meals filtered by category.
 *
 * @param mealType the meal type category
 */
public record GetMealsByMealTypeQuery(MealType mealType) {
    public GetMealsByMealTypeQuery{
        if (mealType == null ){
            throw new IllegalArgumentException("mealType is required and cannot be null");
        }
    }
}

