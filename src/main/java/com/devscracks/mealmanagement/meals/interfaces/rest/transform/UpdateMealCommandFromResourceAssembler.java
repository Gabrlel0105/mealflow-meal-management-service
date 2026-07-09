package com.devscracks.mealmanagement.meals.interfaces.rest.transform;

import com.devscracks.mealmanagement.meals.domain.model.commands.UpdateMealCommand;
import com.devscracks.mealmanagement.meals.domain.model.valueobjects.MealType;
import com.devscracks.mealmanagement.meals.interfaces.rest.resources.UpdateMealResource;

public final class UpdateMealCommandFromResourceAssembler {

    private UpdateMealCommandFromResourceAssembler() {
    }

    public static UpdateMealCommand toCommandFromResource(Long mealId, Long requesterId, UpdateMealResource resource) {
        return new UpdateMealCommand(
                mealId,
                requesterId,
                resource.name(),
                resource.description(),
                resource.price(),
                resource.calories(),
                resource.protein(),
                resource.carbs(),
                resource.fat(),
                MealType.fromString(resource.mealType()),
                resource.imageUrl(),
                resource.available());
    }
}
