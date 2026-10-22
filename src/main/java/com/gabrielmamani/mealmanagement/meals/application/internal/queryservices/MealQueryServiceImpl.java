package com.gabrielmamani.mealmanagement.meals.application.internal.queryservices;

import com.gabrielmamani.mealmanagement.meals.domain.model.aggregates.Meal;
import com.gabrielmamani.mealmanagement.meals.application.queryservices.MealQueryService;
import com.gabrielmamani.mealmanagement.meals.domain.model.queries.*;
import com.gabrielmamani.mealmanagement.meals.domain.repositories.MealRepository;
import com.gabrielmamani.mealmanagement.shared.application.result.ApplicationError;
import com.gabrielmamani.mealmanagement.shared.application.result.Result;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the {@link MealQueryService} application service.
 * <p>Handles read queries for meal catalog items, leveraging Redis caching to optimize throughput.</p>
 */
@Service
@Transactional(readOnly = true)
public class MealQueryServiceImpl implements MealQueryService {

    private final MealRepository mealRepository;

    /**
     * Constructs the service with required repository dependency.
     *
     * @param mealRepository the repository for meal retrieval
     */
    public MealQueryServiceImpl(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    @Cacheable(value = "availableMeals", key = "'all'")
    public List<Meal> handle(GetAllAvailableMealsQuery query) {
        return mealRepository.findByAvailableTrue();
    }
    @Override
    @Cacheable(value = "mealsById", key = "#query.mealId()", unless = "#result.isFailure()")
    public Result<Meal, ApplicationError> handle(GetMealByIdQuery query) {
        return mealRepository.findById(query.mealId())
                .<Result<Meal, ApplicationError>>map(Result::success)
                .orElseGet(() -> Result.failure(ApplicationError.notFound("meal", String.valueOf(query.mealId()))));
    }

    @Override
    @Cacheable(value = "mealsByType", key = "#query.mealType().name()")
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

