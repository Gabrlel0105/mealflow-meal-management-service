package com.devscracks.mealmanagement.meals.application.internal.queryservices;

import com.devscracks.mealmanagement.meals.domain.model.aggregates.Meal;
import com.devscracks.mealmanagement.meals.domain.model.queries.GetAllAvailableMealsQuery;
import com.devscracks.mealmanagement.meals.domain.model.queries.GetMealByIdQuery;
import com.devscracks.mealmanagement.meals.domain.model.queries.GetMealsByMealTypeQuery;
import com.devscracks.mealmanagement.meals.domain.model.queries.GetMealsByProviderIdQuery;
import com.devscracks.mealmanagement.meals.domain.model.queries.GetMyMealsQuery;
import com.devscracks.mealmanagement.meals.domain.services.MealQueryService;
import com.devscracks.mealmanagement.meals.infrastructure.persistence.jpa.repositories.MealRepository;
import com.devscracks.mealmanagement.shared.application.result.ApplicationError;
import com.devscracks.mealmanagement.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MealQueryServiceImpl implements MealQueryService {

    private final MealRepository mealRepository;

    public MealQueryServiceImpl(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public List<Meal> handle(GetAllAvailableMealsQuery query) {
        return mealRepository.findByAvailableTrue();
    }

    @Override
    public Result<Meal, ApplicationError> handle(GetMealByIdQuery query) {
        return mealRepository.findById(query.mealId())
                .<Result<Meal, ApplicationError>>map(Result::success)
                .orElseGet(() -> Result.failure(ApplicationError.notFound("meal", String.valueOf(query.mealId()))));
    }

    @Override
    public List<Meal> handle(GetMealsByMealTypeQuery query) {
        return mealRepository.findByMealTypeAndAvailableTrue(query.mealType());
    }

    @Override
    public List<Meal> handle(GetMealsByProviderIdQuery query) {
        return mealRepository.findByProviderIdAndAvailableTrue(query.providerId());
    }

    @Override
    public List<Meal> handle(GetMyMealsQuery query) {
        return mealRepository.findByProviderId(query.providerId());
    }
}
