package com.gabrielmamani.mealmanagement.meals.infrastructure.persistence.jpa.entities;

import java.util.List;

import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.DietaryCondition;
import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.MealType;
import com.gabrielmamani.mealmanagement.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * JPA entity representing a meal record in the relational database.
 */
@Entity
@Table(name = "meals", indexes = {
        @Index(name = "idx_meals_provider_id", columnList = "provider_id"),
        @Index(name = "idx_meals_meal_type", columnList = "meal_type"),
        @Index(name = "idx_meals_available", columnList = "available")
})
@Getter
@Setter
@NoArgsConstructor
public class MealPersistenceEntity extends AuditableAbstractPersistenceEntity {

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

    @Column(name = "meal_type", nullable = false, length = 20)
    private MealType mealType;

    @Column(nullable = false)
    private boolean available;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "meal_images", joinColumns = @JoinColumn(name = "meal_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    @ElementCollection(targetClass = DietaryCondition.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "meal_dietary_conditions", joinColumns = @JoinColumn(name = "meal_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "dietary_condition")
    private java.util.List<DietaryCondition> dietaryConditions;

    @Column(name = "product_type", length = 100)
    private String productType;

    @Column(name = "country_of_origin", length = 100)
    private String countryOfOrigin;

    @Column(name = "packaging", length = 100)
    private String packaging;
}

