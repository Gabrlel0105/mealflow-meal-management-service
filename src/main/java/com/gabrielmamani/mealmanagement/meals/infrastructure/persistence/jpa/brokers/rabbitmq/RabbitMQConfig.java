package com.gabrielmamani.mealmanagement.meals.infrastructure.persistence.jpa.brokers.rabbitmq;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfig {

    public static final String MEAL_EXCHANGE = "meal.exchange";

    @Bean
    public TopicExchange mealExchange() {
        return new TopicExchange(MEAL_EXCHANGE);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {

        return new Jackson2JsonMessageConverter();
    }
}

