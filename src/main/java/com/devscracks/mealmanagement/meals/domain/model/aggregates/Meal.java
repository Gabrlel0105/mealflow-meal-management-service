package com.devscracks.mealmanagement.meals.domain.model.aggregates;

import com.devscracks.mealmanagement.meals.domain.model.commands.CreateMealCommand;
import com.devscracks.mealmanagement.meals.domain.model.commands.UpdateMealCommand;
import com.devscracks.mealmanagement.meals.domain.model.valueobjects.MealType;
import com.devscracks.mealmanagement.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Meal aggregate root.
 *
 * <p>Represents a dish published by a provider, including its price and nutritional
 * information. A meal is always owned by exactly one provider, and only that provider
 * is allowed to modify or remove it.</p>
 */
@Getter
@Entity
@Table(name = "meals", indexes = {
        @Index(name = "idx_meals_provider_id", columnList = "provider_id"),
        @Index(name = "idx_meals_meal_type", columnList = "meal_type"),
        @Index(name = "idx_meals_available", columnList = "available")
})
public class Meal extends AuditableAbstractPersistenceEntity {

    @Column(name = "provider_id", nullable = false, updatable = false)
    private Long providerId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer calories;

    @Column(nullable = false)
    private Double protein;

    @Column(nullable = false)
    private Double carbs;

    @Column(nullable = false)
    private Double fat;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type", nullable = false, length = 20)
    private MealType mealType;

    @Column(nullable = false)
    private boolean available;

    @Column(name = "image_url")
    private String imageUrl;

    protected Meal() {
        // Required by JPA
    }

    public Meal(CreateMealCommand command) {
        this.providerId = requireNonNull(command.providerId(), "providerId");
        this.mealType = requireNonNull(command.mealType(), "mealType");
        applyDetails(
                command.name(),
                command.description(),
                command.price(),
                command.calories(),
                command.protein(),
                command.carbs(),
                command.fat(),
                command.imageUrl());
        this.available = true;
    }

    /**
     * Applies the changes carried by an {@link UpdateMealCommand} to this meal.
     * Ownership must have already been validated by the caller.
     */
    public void update(UpdateMealCommand command) {
        this.mealType = requireNonNull(command.mealType(), "mealType");
        applyDetails(
                command.name(),
                command.description(),
                command.price(),
                command.calories(),
                command.protein(),
                command.carbs(),
                command.fat(),
                command.imageUrl());
        if (command.available() != null) {
            this.available = command.available();
        }
    }

    /**
     * @return true if the given user is the provider that owns this meal.
     */
    public boolean isOwnedBy(Long requesterId) {
        return this.providerId != null && this.providerId.equals(requesterId);
    }

    private void applyDetails(
            String name,
            String description,
            BigDecimal price,
            Integer calories,
            Double protein,
            Double carbs,
            Double fat,
            String imageUrl) {
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

        this.name = name;
        this.description = description;
        this.price = price;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.imageUrl = imageUrl;
    }

    private static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException("%s is required".formatted(fieldName));
        }
        return value;
    }
}
