package com.gabrielmamani.mealmanagement.meals.domain.model.commands;

import java.util.List;

import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.DietaryCondition;
import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.MealType;

import java.math.BigDecimal;

/**
 * Command containing updated specifications for an existing meal.
 */
public record UpdateMealCommand(Long mealId, Long requesterId, String name, String description, BigDecimal price, Integer calories, Double protein, Double carbs, Double fat, MealType mealType, List<String> imageUrls, Boolean available, java.util.List<DietaryCondition> dietaryConditions, String productType, String countryOfOrigin, String packaging) {

    public UpdateMealCommand {
        if (mealId == null || mealId <= 0) {
            throw new IllegalArgumentException("mealId is required and must be greater than 0");
        }
        if (requesterId == null || requesterId <= 0) {
            throw new IllegalArgumentException("requesterId is required and must be greater than 0");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description cannot be null or blank");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("price cannot be null and must be greater than zero");
        }
        if (calories == null || calories < 0) {
            throw new IllegalArgumentException("calories cannot be null or less than zero");
        }
        if (protein == null || protein < 0) {
            throw new IllegalArgumentException("protein cannot be null or less than zero");
        }
        if (carbs == null || carbs < 0) {
            throw new IllegalArgumentException("carbs cannot be null or less than zero");
        }
        if (fat == null || fat < 0) {
            throw new IllegalArgumentException("fat cannot be null or less than zero");
        }
        if (mealType == null) {
            throw new IllegalArgumentException("mealType cannot be null");
        }
        if (imageUrls == null || imageUrls.isEmpty()) {
            throw new IllegalArgumentException("imageUrls cannot be null or empty");
        }
        if (available == null) {
            throw new IllegalArgumentException("available cannot be null");
        }
    }
}
