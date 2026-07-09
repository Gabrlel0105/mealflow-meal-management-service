package com.devscracks.mealmanagement.meals.domain.services;

import com.devscracks.mealmanagement.meals.domain.model.aggregates.Meal;
import com.devscracks.mealmanagement.meals.domain.model.commands.CreateMealCommand;
import com.devscracks.mealmanagement.meals.domain.model.commands.DeleteMealCommand;
import com.devscracks.mealmanagement.meals.domain.model.commands.UpdateMealCommand;
import com.devscracks.mealmanagement.shared.application.result.ApplicationError;
import com.devscracks.mealmanagement.shared.application.result.Result;

/**
 * Domain service handling all write operations (create/update/delete) on meals.
 */
public interface MealCommandService {

    /**
     * Creates a new meal for the requesting provider.
     */
    Result<Meal, ApplicationError> handle(CreateMealCommand command);

    /**
     * Updates an existing meal. Only the owning provider may perform this operation.
     */
    Result<Meal, ApplicationError> handle(UpdateMealCommand command);

    /**
     * Deletes an existing meal. Only the owning provider may perform this operation.
     */
    Result<Long, ApplicationError> handle(DeleteMealCommand command);
}
