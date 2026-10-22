package com.gabrielmamani.mealmanagement.meals.domain.model.queries;

/**
 * Query to retrieve a meal by its identifier.
 *
 * @param mealId unique meal identifier
 */
public record GetMealByIdQuery(Long mealId) {
    public GetMealByIdQuery {
        if (mealId == null || mealId <= 0) throw new IllegalArgumentException("Meal id is required.");
    }
}

