package com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects;

/**
 * Enumeration of standard meal categories in the catalog.
 */
public enum MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK;

    public static MealType fromString(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            throw new IllegalArgumentException(
                    "mealType is required. Allowed values: BREAKFAST, LUNCH, DINNER, SNACK");
        }
        try {
            return MealType.valueOf(rawValue.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Invalid mealType '%s'. Allowed values: BREAKFAST, LUNCH, DINNER, SNACK".formatted(rawValue));
        }
    }
}

