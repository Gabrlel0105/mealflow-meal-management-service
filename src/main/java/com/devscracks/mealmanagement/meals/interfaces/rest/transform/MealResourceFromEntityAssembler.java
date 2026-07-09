package com.devscracks.mealmanagement.meals.interfaces.rest.transform;

import com.devscracks.mealmanagement.meals.domain.model.aggregates.Meal;
import com.devscracks.mealmanagement.meals.interfaces.rest.resources.MealResource;

public final class MealResourceFromEntityAssembler {

    private MealResourceFromEntityAssembler() {
    }

    public static MealResource toResourceFromEntity(Meal meal) {
        return new MealResource(
                meal.getId(),
                meal.getProviderId(),
                meal.getName(),
                meal.getDescription(),
                meal.getPrice(),
                meal.getCalories(),
                meal.getProtein(),
                meal.getCarbs(),
                meal.getFat(),
                meal.getMealType().name(),
                meal.isAvailable(),
                meal.getImageUrl(),
                meal.getCreatedAt(),
                meal.getUpdatedAt());
    }
}
