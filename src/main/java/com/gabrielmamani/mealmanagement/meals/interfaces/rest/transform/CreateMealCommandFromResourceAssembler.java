package com.gabrielmamani.mealmanagement.meals.interfaces.rest.transform;

import com.gabrielmamani.mealmanagement.meals.domain.model.commands.CreateMealCommand;
import com.gabrielmamani.mealmanagement.meals.interfaces.rest.resources.CreateMealResource;
import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.DietaryCondition;
import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.MealType;

import java.util.ArrayList;
import java.util.List;

/**
 * Assembler to transform {@link CreateMealResource} representations into {@link CreateMealCommand} domain commands.
 */
public class CreateMealCommandFromResourceAssembler {

    public static CreateMealCommand toCommandFromResource(Long providerId, CreateMealResource resource) {
        List<DietaryCondition> dietaryConditions = new ArrayList<>();
        if (resource.dietaryConditions() != null) {
            for(String dc : resource.dietaryConditions()) {
                if(dc != null && !dc.isBlank()) {
                    dietaryConditions.add(DietaryCondition.valueOf(dc.toUpperCase()));
                }
            }
        }

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
                resource.imageUrls(),
                dietaryConditions,
                resource.productType(),
                resource.countryOfOrigin(),
                resource.packaging());
    }
}
