package com.devscracks.mealmanagement.meals.domain.model.queries;

/**
 * Query for a provider to list all of their own meals, including unavailable ones.
 */
public record GetMyMealsQuery(Long providerId) {
}
