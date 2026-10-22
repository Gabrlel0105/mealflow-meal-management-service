package com.gabrielmamani.mealmanagement.meals.application.queryservices;

import com.gabrielmamani.mealmanagement.meals.domain.model.aggregates.Meal;
import com.gabrielmamani.mealmanagement.meals.domain.model.queries.GetAllAvailableMealsQuery;
import com.gabrielmamani.mealmanagement.meals.domain.model.queries.GetMealByIdQuery;
import com.gabrielmamani.mealmanagement.meals.domain.model.queries.GetMealsByMealTypeQuery;
import com.gabrielmamani.mealmanagement.meals.domain.model.queries.GetMealsByProviderIdQuery;
import com.gabrielmamani.mealmanagement.meals.domain.model.queries.GetMyMealsQuery;
import com.gabrielmamani.mealmanagement.shared.application.result.ApplicationError;
import com.gabrielmamani.mealmanagement.shared.application.result.Result;

import java.util.List;

/**
 * Application service contract for queries over the {@link Meal} aggregate.
 */
public interface MealQueryService {

    /**
     * Retrieves all meals marked as available across all providers.
     *
     * @param query query requesting available catalog meals
     * @return list of available meal aggregates
     * @see GetAllAvailableMealsQuery
     */
    List<Meal> handle(GetAllAvailableMealsQuery query);

    /**
     * Retrieves a single meal by its identifier.
     *
     * @param query query containing the target meal identifier
     * @return meal aggregate if found, or an application error
     * @see GetMealByIdQuery
     */
    Result<Meal, ApplicationError> handle(GetMealByIdQuery query);

    /**
     * Retrieves available meals filtered by meal type.
     *
     * @param query query containing the target meal type
     * @return list of matching meal aggregates
     * @see GetMealsByMealTypeQuery
     */
    List<Meal> handle(GetMealsByMealTypeQuery query);

    /**
     * Retrieves available meals published by a specific provider.
     *
     * @param query query containing the target provider identifier
     * @return list of meal aggregates belonging to the provider
     * @see GetMealsByProviderIdQuery
     */
    List<Meal> handle(GetMealsByProviderIdQuery query);

    /**
     * Retrieves all meals (available and unavailable) belonging to the authenticated provider.
     *
     * @param query query containing the authenticated provider's identifier
     * @return list of the provider's meals
     * @see GetMyMealsQuery
     */
    List<Meal> handle(GetMyMealsQuery query);
}

