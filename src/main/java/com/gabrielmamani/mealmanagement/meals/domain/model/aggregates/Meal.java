package com.gabrielmamani.mealmanagement.meals.domain.model.aggregates;

import java.util.List;

import com.gabrielmamani.mealmanagement.meals.domain.model.commands.CreateMealCommand;
import com.gabrielmamani.mealmanagement.meals.domain.model.commands.UpdateMealCommand;
import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.DietaryCondition;
import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.MealType;

import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;

/**
 * Aggregate root representing a meal in the Mealflow catalog.
 * <p>Contains dish details, nutritional profile, provider ownership, and dietary restrictions.</p>
 */
@Getter
public class Meal extends AbstractAggregateRoot<Meal> {

    @Setter
    private Long id;

    @Setter
    private Long providerId;

    @Setter
    private String name;

    @Setter
    private String description;

    @Setter
    private BigDecimal price;

    @Setter
    private Integer calories;

    @Setter
    private Double protein;

    @Setter
    private Double carbs;

    @Setter
    private Double fat;

    @Setter
    private MealType mealType;

    @ElementCollection(targetClass = DietaryCondition.class)
    @jakarta.persistence.Enumerated(jakarta.persistence.EnumType.STRING)
    @Setter
    private java.util.List<DietaryCondition> dietaryConditions;

    @Setter
    private boolean available;

    @ElementCollection
    @Setter
    private List<String> imageUrls;

    @Setter
    private String productType;

    @Setter
    private String countryOfOrigin;

    @Setter
    private String packaging;

    public Meal() {
    }

    public Meal(CreateMealCommand command) {
        this.providerId = requireNonNull(command.providerId(), "providerId");
        this.mealType = requireNonNull(command.mealType(), "mealType");
        this.dietaryConditions = command.dietaryConditions();
        applyDetails(
                command.name(),
                command.description(),
                command.price(),
                command.calories(),
                command.protein(),
                command.carbs(),
                command.fat(),
                command.imageUrls(),
                command.productType(),
                command.countryOfOrigin(),
                command.packaging());
        this.available = true;
    }

    public void update(UpdateMealCommand command) {
        this.mealType = requireNonNull(command.mealType(), "mealType");
        if (command.dietaryConditions() != null) {
            this.dietaryConditions = command.dietaryConditions();
        }
        applyDetails(
                command.name(),
                command.description(),
                command.price(),
                command.calories(),
                command.protein(),
                command.carbs(),
                command.fat(),
                command.imageUrls(),
                command.productType(),
                command.countryOfOrigin(),
                command.packaging());
        if (command.available() != null) {
            this.available = command.available();
        }
    }

    public boolean isOwnedBy(Long requesterId) {
        return this.providerId != null && this.providerId.equals(requesterId);
    }

    private void applyDetails(String name, String description, BigDecimal price, Integer calories, Double protein, Double carbs, Double fat, List<String> imageUrls, String productType, String countryOfOrigin, String packaging) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.imageUrls = imageUrls;
        this.productType = productType;
        this.countryOfOrigin = countryOfOrigin;
        this.packaging = packaging;
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException("%s is required".formatted(fieldName));
        }
        return value;
    }
}

