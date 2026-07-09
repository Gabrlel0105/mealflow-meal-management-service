package com.devscracks.mealmanagement.meals.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Schema(name = "CreateMealRequest", description = "Payload to create a new meal in the provider's catalog")
public record CreateMealResource(

        @Schema(description = "Name of the meal", example = "Grilled Chicken Bowl")
        @NotBlank(message = "name is required")
        String name,

        @Schema(description = "Description of the meal", example = "Grilled chicken breast with quinoa, avocado and steamed vegetables")
        @NotBlank(message = "description is required")
        String description,

        @Schema(description = "Price of the meal, must be greater than 0", example = "12.50")
        @NotNull(message = "price is required")
        BigDecimal price,

        @Schema(description = "Total calories (kcal)", example = "520")
        @NotNull(message = "calories is required")
        @PositiveOrZero(message = "calories must be greater than or equal to 0")
        Integer calories,

        @Schema(description = "Protein in grams", example = "38.5")
        @NotNull(message = "protein is required")
        @PositiveOrZero(message = "protein must be greater than or equal to 0")
        Double protein,

        @Schema(description = "Carbohydrates in grams", example = "45.0")
        @NotNull(message = "carbs is required")
        @PositiveOrZero(message = "carbs must be greater than or equal to 0")
        Double carbs,

        @Schema(description = "Fat in grams", example = "14.2")
        @NotNull(message = "fat is required")
        @PositiveOrZero(message = "fat must be greater than or equal to 0")
        Double fat,

        @Schema(description = "Type of meal", example = "LUNCH", allowableValues = {"BREAKFAST", "LUNCH", "DINNER", "SNACK"})
        @NotBlank(message = "mealType is required")
        String mealType,

        @Schema(description = "Optional image URL", example = "https://cdn.mealflow.com/meals/grilled-chicken-bowl.jpg")
        String imageUrl) {
}
