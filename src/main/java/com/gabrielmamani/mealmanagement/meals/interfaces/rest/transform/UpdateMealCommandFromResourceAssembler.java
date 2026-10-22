package com.gabrielmamani.mealmanagement.meals.interfaces.rest.transform;

import com.gabrielmamani.mealmanagement.meals.domain.model.commands.UpdateMealCommand;
import com.gabrielmamani.mealmanagement.meals.interfaces.rest.resources.UpdateMealResource;
import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.DietaryCondition;
import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.MealType;

import java.util.ArrayList;
import java.util.List;

/**
 * Assembler to transform {@link UpdateMealResource} representations into {@link UpdateMealCommand} domain commands.
 */
public class UpdateMealCommandFromResourceAssembler {

    public static UpdateMealCommand toCommandFromResource(Long mealId, Long requesterId, UpdateMealResource resource) {
        List<DietaryCondition> dietaryConditions = new ArrayList<>();
        if (resource.dietaryConditions() != null) {
            for(String dc : resource.dietaryConditions()) {
                if(dc != null && !dc.isBlank()) {
                    dietaryConditions.add(DietaryCondition.valueOf(dc.toUpperCase()));
                }
            }
        }

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
                resource.imageUrls(),
                resource.available(),
                dietaryConditions,
                resource.productType(),
                resource.countryOfOrigin(),
                resource.packaging());
    }
}

