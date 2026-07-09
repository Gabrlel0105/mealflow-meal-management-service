package com.devscracks.mealmanagement.meals.domain.model.valueobjects;

/**
 * Value object representing the category of a meal within the daily menu.
 */
public enum MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK;

    /**
     * Parses a raw string into a {@link MealType}, case-insensitively.
     *
     * @param rawValue the raw value received from a request
     * @return the matching MealType
     * @throws IllegalArgumentException if the value is blank or does not match any known meal type
     */
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
