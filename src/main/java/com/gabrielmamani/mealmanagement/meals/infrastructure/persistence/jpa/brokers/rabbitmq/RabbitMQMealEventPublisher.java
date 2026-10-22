package com.gabrielmamani.mealmanagement.meals.infrastructure.persistence.jpa.brokers.rabbitmq;

import com.gabrielmamani.mealmanagement.meals.application.events.MealEventPublisher;
import com.gabrielmamani.mealmanagement.meals.domain.model.events.MealCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ adapter implementing {@link MealEventPublisher} to broadcast domain events.
 */
@Component
public class RabbitMQMealEventPublisher implements MealEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQMealEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishMealCreatedEvent(MealCreatedEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.MEAL_EXCHANGE, "meal.created", event);
        System.out.println("Evento publicado exitosamente en la cola Rabbit : " + event.mealId());
    }
}
