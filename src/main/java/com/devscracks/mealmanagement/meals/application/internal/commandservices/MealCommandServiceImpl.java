package com.devscracks.mealmanagement.meals.application.internal.commandservices;

import com.devscracks.mealmanagement.meals.domain.model.aggregates.Meal;
import com.devscracks.mealmanagement.meals.domain.model.commands.CreateMealCommand;
import com.devscracks.mealmanagement.meals.domain.model.commands.DeleteMealCommand;
import com.devscracks.mealmanagement.meals.domain.model.commands.UpdateMealCommand;
import com.devscracks.mealmanagement.meals.domain.services.MealCommandService;
import com.devscracks.mealmanagement.meals.infrastructure.persistence.jpa.repositories.MealRepository;
import com.devscracks.mealmanagement.shared.application.result.ApplicationError;
import com.devscracks.mealmanagement.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class MealCommandServiceImpl implements MealCommandService {

    private final MealRepository mealRepository;

    public MealCommandServiceImpl(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Result<Meal, ApplicationError> handle(CreateMealCommand command) {
        try {
            var meal = new Meal(command);
            var savedMeal = mealRepository.save(meal);
            return Result.success(savedMeal);
        } catch (IllegalArgumentException ex) {
            return Result.failure(ApplicationError.validationError("meal", ex.getMessage()));
        }
    }

    @Override
    public Result<Meal, ApplicationError> handle(UpdateMealCommand command) {
        var mealOptional = mealRepository.findById(command.mealId());
        if (mealOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("meal", String.valueOf(command.mealId())));
        }

        var meal = mealOptional.get();
        if (!meal.isOwnedBy(command.requesterId())) {
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
    public Result<Long, ApplicationError> handle(DeleteMealCommand command) {
        var mealOptional = mealRepository.findById(command.mealId());
        if (mealOptional.isEmpty()) {
            return Result.failure(ApplicationError.notFound("meal", String.valueOf(command.mealId())));
        }

        var meal = mealOptional.get();
        if (!meal.isOwnedBy(command.requesterId())) {
            return Result.failure(ApplicationError.forbidden(
                    "delete-meal",
                    "Only the provider that created this meal can delete it"));
        }

        mealRepository.delete(meal);
        return Result.success(command.mealId());
    }
}
