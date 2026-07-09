package com.devscracks.mealmanagement.meals.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(name = "MealResponse", description = "Meal catalog entry, including nutritional information")
public record MealResource(

        @Schema(description = "Meal identifier", example = "1")
        Long id,

        @Schema(description = "Identifier of the provider that owns this meal", example = "42")
        Long providerId,

        @Schema(description = "Name of the meal", example = "Grilled Chicken Bowl")
        String name,

        @Schema(description = "Description of the meal", example = "Grilled chicken breast with quinoa, avocado and steamed vegetables")
        String description,

        @Schema(description = "Price of the meal", example = "12.50")
        BigDecimal price,

        @Schema(description = "Total calories (kcal)", example = "520")
        Integer calories,

        @Schema(description = "Protein in grams", example = "38.5")
        Double protein,

        @Schema(description = "Carbohydrates in grams", example = "45.0")
        Double carbs,

        @Schema(description = "Fat in grams", example = "14.2")
        Double fat,

        @Schema(description = "Type of meal", example = "LUNCH")
        String mealType,

        @Schema(description = "Whether the meal is currently available for purchase", example = "true")
        boolean available,

        @Schema(description = "Optional image URL", example = "https://cdn.mealflow.com/meals/grilled-chicken-bowl.jpg")
        String imageUrl,

        @Schema(description = "Creation timestamp")
        LocalDateTime createdAt,

        @Schema(description = "Last update timestamp")
        LocalDateTime updatedAt) {
}
