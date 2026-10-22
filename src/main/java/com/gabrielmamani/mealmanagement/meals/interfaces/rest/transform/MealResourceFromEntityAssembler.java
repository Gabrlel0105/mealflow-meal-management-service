package com.gabrielmamani.mealmanagement.meals.interfaces.rest.transform;

import com.gabrielmamani.mealmanagement.meals.domain.model.aggregates.Meal;
import com.gabrielmamani.mealmanagement.meals.interfaces.rest.resources.MealResource;

/**
 * Assembler to transform {@link Meal} domain aggregates into {@link MealResource} REST representations.
 */
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
                meal.getImageUrls(),
                meal.getDietaryConditions() != null ? meal.getDietaryConditions().stream().map(Enum::name).toList() : null,
                meal.getProductType(),
                meal.getCountryOfOrigin(),
                meal.getPackaging());
    }
}

