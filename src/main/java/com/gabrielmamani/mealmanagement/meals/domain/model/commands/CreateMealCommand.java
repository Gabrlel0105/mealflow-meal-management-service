package com.gabrielmamani.mealmanagement.meals.domain.model.commands;

import java.util.List;

import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.DietaryCondition;
import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.MealType;

import java.math.BigDecimal;

/**
 * Command containing payload data to create a new meal in the catalog.
 */
public record CreateMealCommand(Long providerId, String name, String description, BigDecimal price, Integer calories, Double protein, Double carbs, Double fat, MealType mealType, List<String> imageUrls, java.util.List<DietaryCondition> dietaryConditions, String productType, String countryOfOrigin, String packaging) {

public CreateMealCommand{
    if (name == null || name.isBlank()) {
        throw new IllegalArgumentException("name is required");
    }
    if (description == null || description.isBlank()) {
        throw new IllegalArgumentException("description is required");
    }
    if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("price must be greater than 0");
    }
    if (calories == null || calories < 0) {
        throw new IllegalArgumentException("calories must be greater than or equal to 0");
    }
    if (protein == null || protein < 0) {
        throw new IllegalArgumentException("protein must be greater than or equal to 0");
    }
    if (carbs == null || carbs < 0) {
        throw new IllegalArgumentException("carbs must be greater than or equal to 0");
    }
    if (fat == null || fat < 0) {
        throw new IllegalArgumentException("fat must be greater than or equal to 0");
    }
}
}

