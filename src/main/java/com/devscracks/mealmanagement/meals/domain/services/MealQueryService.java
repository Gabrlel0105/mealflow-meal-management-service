package com.devscracks.mealmanagement.meals.domain.services;

import com.devscracks.mealmanagement.meals.domain.model.aggregates.Meal;
import com.devscracks.mealmanagement.meals.domain.model.queries.GetAllAvailableMealsQuery;
import com.devscracks.mealmanagement.meals.domain.model.queries.GetMealByIdQuery;
import com.devscracks.mealmanagement.meals.domain.model.queries.GetMealsByMealTypeQuery;
import com.devscracks.mealmanagement.meals.domain.model.queries.GetMealsByProviderIdQuery;
import com.devscracks.mealmanagement.meals.domain.model.queries.GetMyMealsQuery;
import com.devscracks.mealmanagement.shared.application.result.ApplicationError;
import com.devscracks.mealmanagement.shared.application.result.Result;

import java.util.List;

/**
 * Domain service handling all read operations (list/search) on meals.
 */
public interface MealQueryService {

    /**
     * Lists every meal currently available, across all providers.
     */
    List<Meal> handle(GetAllAvailableMealsQuery query);

    /**
     * Retrieves a single meal by its identifier.
     */
    Result<Meal, ApplicationError> handle(GetMealByIdQuery query);

    /**
     * Lists all available meals matching the given meal type.
     */
    List<Meal> handle(GetMealsByMealTypeQuery query);

    /**
     * Lists the available meals published by a specific provider.
     */
    List<Meal> handle(GetMealsByProviderIdQuery query);

    /**
     * Lists all meals (available and unavailable) owned by the requesting provider.
     */
    List<Meal> handle(GetMyMealsQuery query);
}
