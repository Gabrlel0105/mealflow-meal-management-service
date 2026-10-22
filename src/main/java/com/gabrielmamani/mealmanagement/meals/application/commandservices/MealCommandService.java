package com.gabrielmamani.mealmanagement.meals.application.commandservices;

import com.gabrielmamani.mealmanagement.meals.domain.model.aggregates.Meal;
import com.gabrielmamani.mealmanagement.meals.domain.model.commands.CreateMealCommand;
import com.gabrielmamani.mealmanagement.meals.domain.model.commands.DeleteMealCommand;
import com.gabrielmamani.mealmanagement.meals.domain.model.commands.UpdateMealCommand;
import com.gabrielmamani.mealmanagement.shared.application.result.ApplicationError;
import com.gabrielmamani.mealmanagement.shared.application.result.Result;

/**
 * Application service contract for command execution over the {@link Meal} aggregate.
 */
public interface MealCommandService {

    /**
     * Handles meal creation in the catalog of the requesting provider.
     *
     * @param command command containing meal details, nutritional info, and dietary conditions
     * @return created meal aggregate or an application error
     * @see CreateMealCommand
     */
    Result<Meal, ApplicationError> handle(CreateMealCommand command);

    /**
     * Handles meal update, validating ownership of the requesting provider.
     *
     * @param command command containing updated meal information
     * @return updated meal aggregate or an application error
     * @see UpdateMealCommand
     */
    Result<Meal, ApplicationError> handle(UpdateMealCommand command);

    /**
     * Handles meal deletion from the catalog.
     *
     * @param command command containing target meal identifier and provider identifier
     * @return deleted meal identifier or an application error
     * @see DeleteMealCommand
     */
    Result<Long, ApplicationError> handle(DeleteMealCommand command);
}

