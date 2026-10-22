
package com.gabrielmamani.mealmanagement.meals.infrastructure.persistence.jpa.adapters;

import com.gabrielmamani.mealmanagement.meals.domain.model.aggregates.Meal;
import com.gabrielmamani.mealmanagement.meals.domain.model.valueobjects.MealType;
import com.gabrielmamani.mealmanagement.meals.domain.repositories.MealRepository;
import com.gabrielmamani.mealmanagement.meals.infrastructure.persistence.jpa.assemblers.MealPersistenceAssembler;
import com.gabrielmamani.mealmanagement.meals.infrastructure.persistence.jpa.repositories.MealPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter implementing the domain {@link MealRepository} port.
 */
@Repository
public class MealRepositoryImpl implements MealRepository {
    private final MealPersistenceRepository mealPersistenceRepository;

    public MealRepositoryImpl(MealPersistenceRepository mealPersistenceRepository) {
        this.mealPersistenceRepository = mealPersistenceRepository;
    }

    @Override
    public Optional<Meal> findById(Long id) {
        return mealPersistenceRepository.findById(id)
                .map(MealPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Meal> findAll() {
        return mealPersistenceRepository.findAll().stream()
                .map(MealPersistenceAssembler::toDomainFromPersistence)
                .collect(java.util.stream.Collectors.toCollection(java.util.ArrayList::new));
    }

    @Override
    public Meal save(Meal meal) {
        var savedEntity = mealPersistenceRepository.save(MealPersistenceAssembler.toPersistenceFromDomain(meal));
        return MealPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        mealPersistenceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return mealPersistenceRepository.existsById(id);
    }

    @Override
    public List<Meal> findByAvailableTrue() {
        return mealPersistenceRepository.findByAvailableTrue().stream()
                .map(MealPersistenceAssembler::toDomainFromPersistence)
                .collect(java.util.stream.Collectors.toCollection(java.util.ArrayList::new));
    }

    @Override
    public List<Meal> findByMealTypeAndAvailableTrue(MealType mealType) {
        return mealPersistenceRepository.findByMealTypeAndAvailableTrue(mealType).stream()
                .map(MealPersistenceAssembler::toDomainFromPersistence)
                .collect(java.util.stream.Collectors.toCollection(java.util.ArrayList::new));
    }

    @Override
    public List<Meal> findByProviderIdAndAvailableTrue(Long providerId) {
        return mealPersistenceRepository.findByProviderIdAndAvailableTrue(providerId).stream()
                .map(MealPersistenceAssembler::toDomainFromPersistence)
                .collect(java.util.stream.Collectors.toCollection(java.util.ArrayList::new));
    }

    @Override
    public List<Meal> findByProviderId(Long providerId) {
        return mealPersistenceRepository.findByProviderId(providerId).stream()
                .map(MealPersistenceAssembler::toDomainFromPersistence)
                .collect(java.util.stream.Collectors.toCollection(java.util.ArrayList::new));
    }

}

