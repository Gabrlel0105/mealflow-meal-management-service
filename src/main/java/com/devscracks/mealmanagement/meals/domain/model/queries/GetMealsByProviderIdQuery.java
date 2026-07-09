package com.devscracks.mealmanagement.meals.domain.model.queries;

/**
 * Query to list the available meals published by a specific provider, as seen by a consumer.
 */
public record GetMealsByProviderIdQuery(Long providerId) {
}
