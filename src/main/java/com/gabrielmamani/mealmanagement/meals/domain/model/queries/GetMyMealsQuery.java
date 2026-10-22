package com.gabrielmamani.mealmanagement.meals.domain.model.queries;

/**
 * Query to retrieve all meals published by the authenticated provider.
 *
 * @param providerId unique provider identifier
 */
public record GetMyMealsQuery(Long providerId) {
    public GetMyMealsQuery{
        if (providerId == null || providerId <= 0) {
            throw new IllegalArgumentException("providerId is required and must be greater than 0");
        }
    }
}

