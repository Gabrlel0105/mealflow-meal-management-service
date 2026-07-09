package com.devscracks.mealmanagement.meals.interfaces.rest.transform;

import com.devscracks.mealmanagement.meals.domain.model.commands.CreateMealCommand;
import com.devscracks.mealmanagement.meals.domain.model.valueobjects.MealType;
import com.devscracks.mealmanagement.meals.interfaces.rest.resources.CreateMealResource;

public final class CreateMealCommandFromResourceAssembler {

    private CreateMealCommandFromResourceAssembler() {
    }

    public static CreateMealCommand toCommandFromResource(Long providerId, CreateMealResource resource) {
        return new CreateMealCommand(
                providerId,
                resource.name(),
                resource.description(),
                resource.price(),
                resource.calories(),
                resource.protein(),
                resource.carbs(),
                resource.fat(),
                MealType.fromString(resource.mealType()),
                resource.imageUrl());
    }
}
