package com.gabrielmamani.mealmanagement.meals.application.events;

import com.gabrielmamani.mealmanagement.meals.domain.model.events.MealCreatedEvent;

/**
 * Contract for publishing meal domain events to external messaging brokers.
 */
public interface MealEventPublisher {

    /**
     * Publishes a {@link MealCreatedEvent} when a new meal is created.
     *
     * @param event the event payload containing meal identification and details
     */
    void publishMealCreatedEvent(MealCreatedEvent event);
}

