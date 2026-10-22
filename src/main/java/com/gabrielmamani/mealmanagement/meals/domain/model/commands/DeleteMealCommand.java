package com.gabrielmamani.mealmanagement.meals.domain.model.commands;

/**
 * Command to delete a meal from the catalog.
 *
 * @param mealId      target meal identifier
 * @param requesterId identifier of the requesting provider
 */
public record DeleteMealCommand(Long mealId, Long requesterId) {
    public DeleteMealCommand{
        if (mealId == null || mealId <= 0) {
            throw new IllegalArgumentException("mealId is required and must be greater than 0");
    }
        if (requesterId == null || requesterId <= 0) {
            throw new IllegalArgumentException("requesterId is required and must be greater than 0");
        }
    }
}

