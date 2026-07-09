package com.devscracks.mealmanagement.meals.domain.model.commands;

/**
 * Command to delete an existing meal. Only the provider that created the meal may perform this operation.
 */
public record DeleteMealCommand(Long mealId, Long requesterId) {
}
