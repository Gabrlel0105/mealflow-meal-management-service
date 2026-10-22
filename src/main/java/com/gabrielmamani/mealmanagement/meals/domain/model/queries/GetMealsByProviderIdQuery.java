package com.gabrielmamani.mealmanagement.meals.domain.model.queries;

/**
 * Query to retrieve available meals belonging to a specific provider.
 *
 * @param providerId unique provider identifier
 */
public record GetMealsByProviderIdQuery(Long providerId) {
    public GetMealsByProviderIdQuery{
        if (providerId == null || providerId <= 0) {
            throw new IllegalArgumentException("providerId is required and must be greater than 0");
        }
    }
}

