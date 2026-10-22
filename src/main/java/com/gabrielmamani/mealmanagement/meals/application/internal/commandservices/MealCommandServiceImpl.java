package com.gabrielmamani.mealmanagement.meals.application.internal.commandservices;

import com.gabrielmamani.mealmanagement.meals.application.commandservices.MealCommandService;
import com.gabrielmamani.mealmanagement.meals.application.events.MealEventPublisher;
import com.gabrielmamani.mealmanagement.meals.domain.model.aggregates.Meal;
import com.gabrielmamani.mealmanagement.meals.domain.model.commands.CreateMealCommand;
import com.gabrielmamani.mealmanagement.meals.domain.model.commands.DeleteMealCommand;
import com.gabrielmamani.mealmanagement.meals.domain.model.commands.UpdateMealCommand;
import com.gabrielmamani.mealmanagement.meals.domain.model.events.MealCreatedEvent;
import com.gabrielmamani.mealmanagement.meals.domain.repositories.MealRepository;
import com.gabrielmamani.mealmanagement.shared.application.result.ApplicationError;
import com.gabrielmamani.mealmanagement.shared.application.result.Result;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link MealCommandService} application service.
 * <p>Coordinates business operations for meal aggregate creation, updates, and removal,
 * ensuring access control and cache evictions.</p>
 */
@Service
public class MealCommandServiceImpl implements MealCommandService {

    private final MealRepository mealRepository;
    private final MealEventPublisher mealEventPublisher;

    /**
     * Constructs the service with required repository and event publisher dependencies.
     *
     * @param mealRepository     the repository for meal persistence
     * @param mealEventPublisher the publisher for publishing domain events
     */
    public MealCommandServiceImpl(MealRepository mealRepository, MealEventPublisher mealEventPublisher) {
        this.mealRepository = mealRepository;
        this.mealEventPublisher = mealEventPublisher;
    }

    private boolean isAdmin() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities() == null) return false;
        return auth.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "availableMeals", allEntries = true, condition = "#result != null && #result.isSuccess()"),
            @CacheEvict(value = "mealsByType", allEntries = true, condition = "#result != null && #result.isSuccess()")
    })
    public Result<Meal, ApplicationError> handle(CreateMealCommand command) {
        try {
            var meal = new Meal(command);
            var savedMeal = mealRepository.save(meal);

            MealCreatedEvent event = new MealCreatedEvent(savedMeal.getId(), savedMeal.getProviderId(),savedMeal.getName());
            mealEventPublisher.publishMealCreatedEvent(event);

            return Result.success(savedMeal);

        } catch (IllegalArgumentException ex) {
            return Result.failure(ApplicationError.validationError("meal", ex.getMessage()));
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "mealsById", key = "#command.mealId()", condition = "#result != null && #result.isSuccess()"),
            @CacheEvict(value = "availableMeals", allEntries = true, condition = "#result != null && #result.isSuccess()"),
            @CacheEvict(value = "mealsByType", allEntries = true, condition = "#result != null && #result.isSuccess()")
    })
    public Result<Meal, ApplicationError> handle(UpdateMealCommand command) {
        var mealOptional = mealRepository.findById(command.mealId());
        if (mealOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("meal", String.valueOf(command.mealId())));
        }

        var meal = mealOptional.get();
        if (!isAdmin() && !meal.isOwnedBy(command.requesterId())) {
            return Result.failure(ApplicationError.forbidden(
                    "update-meal",
                    "Only the provider that created this meal can update it"));
        }

        try {
            meal.update(command);
            var savedMeal = mealRepository.save(meal);
            return Result.success(savedMeal);
        } catch (IllegalArgumentException ex) {
            return Result.failure(ApplicationError.validationError("meal", ex.getMessage()));
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "mealsById", key = "#command.mealId()", condition = "#result != null && #result.isSuccess()"),
            @CacheEvict(value = "availableMeals", allEntries = true, condition = "#result != null && #result.isSuccess()"),
            @CacheEvict(value = "mealsByType", allEntries = true, condition = "#result != null && #result.isSuccess()")
    })
    public Result<Long, ApplicationError> handle(DeleteMealCommand command) {
        var mealOptional = mealRepository.findById(command.mealId());
        if (mealOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("meal", String.valueOf(command.mealId())));
        }

        var meal = mealOptional.get();
        if (!isAdmin() && !meal.isOwnedBy(command.requesterId())) {
            return Result.failure(ApplicationError.forbidden(
                    "delete-meal",
                    "Only the provider that created this meal can delete it"));
        }

        mealRepository.deleteById(command.mealId());
        return Result.success(command.mealId());

    }
}

