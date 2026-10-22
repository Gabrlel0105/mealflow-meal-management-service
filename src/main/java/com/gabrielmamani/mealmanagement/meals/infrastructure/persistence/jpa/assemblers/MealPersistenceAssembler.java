package com.gabrielmamani.mealmanagement.meals.infrastructure.persistence.jpa.assemblers;

import com.gabrielmamani.mealmanagement.meals.domain.model.aggregates.Meal;
import com.gabrielmamani.mealmanagement.meals.infrastructure.persistence.jpa.entities.MealPersistenceEntity;

/**
 * Static assembler mapping between {@link Meal} domain aggregates and {@link MealPersistenceEntity} JPA representations.
 */
public final class MealPersistenceAssembler {

    private MealPersistenceAssembler() {
    }

    /**
     * Converts a JPA persistence entity into a domain aggregate.
     *
     * @param entity the JPA entity from the database
     * @return the reconstructed {@link Meal} domain aggregate
     */
    public static Meal toDomainFromPersistence(MealPersistenceEntity entity) {
        if (entity == null) return null;

        var meal = new Meal();
        meal.setId(entity.getId());
        meal.setProviderId(entity.getProviderId());
        meal.setName(entity.getName());
        meal.setDescription(entity.getDescription());
        meal.setPrice(entity.getPrice());
        meal.setCalories(entity.getCalories());
        meal.setProtein(entity.getProtein());
        meal.setCarbs(entity.getCarbs());
        meal.setFat(entity.getFat());
        meal.setMealType(entity.getMealType());
        meal.setAvailable(entity.isAvailable());
        meal.setImageUrls(entity.getImageUrls() != null ? new java.util.ArrayList<>(entity.getImageUrls()) : new java.util.ArrayList<>());
        meal.setDietaryConditions(entity.getDietaryConditions() != null ? new java.util.ArrayList<>(entity.getDietaryConditions()) : new java.util.ArrayList<>());
        meal.setProductType(entity.getProductType());
        meal.setCountryOfOrigin(entity.getCountryOfOrigin());
        meal.setPackaging(entity.getPackaging());

        return meal;
    }

    /**
     * Converts a domain aggregate into a JPA persistence entity.
     *
     * @param meal the {@link Meal} domain aggregate
     * @return the {@link MealPersistenceEntity} ready to be stored in the database
     */
    public static MealPersistenceEntity toPersistenceFromDomain(Meal meal) {
        if (meal == null) return null;

        var entity = new MealPersistenceEntity();
        if (meal.getId() != null) {
            entity.setId(meal.getId());
        }
        entity.setProviderId(meal.getProviderId());
        entity.setName(meal.getName());
        entity.setDescription(meal.getDescription());
        entity.setPrice(meal.getPrice());
        entity.setCalories(meal.getCalories());
        entity.setProtein(meal.getProtein());
        entity.setCarbs(meal.getCarbs());
        entity.setFat(meal.getFat());
        entity.setMealType(meal.getMealType());
        entity.setAvailable(meal.isAvailable());
        entity.setImageUrls(meal.getImageUrls());
        entity.setDietaryConditions(meal.getDietaryConditions());
        entity.setProductType(meal.getProductType());
        entity.setCountryOfOrigin(meal.getCountryOfOrigin());
        entity.setPackaging(meal.getPackaging());

        return entity;
    }
}
