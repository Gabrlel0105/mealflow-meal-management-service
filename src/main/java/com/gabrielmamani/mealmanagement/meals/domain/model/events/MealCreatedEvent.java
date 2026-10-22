package com.gabrielmamani.mealmanagement.meals.domain.model.events;

/**
 * Domain event published when a new meal is successfully registered.
 */
public record MealCreatedEvent(Long mealId, Long providerId, String name) {}
