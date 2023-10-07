package com.scait.data.validator.impl;

import com.scait.data.validator.FailedCondition;
import com.scait.data.validator.ValidationResult;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * Concurrent implementation of {@link ValidationResult} class.
 */
class ConcurrentDataValidationResult implements ValidationResult {

    private final Collection<FailedCondition> failedConditions = new ConcurrentLinkedQueue<>();

    /**
     * Default constructor.
     */
    ConcurrentDataValidationResult() {
        //No op.
    }

    @Override
    public boolean isSuccessful() {
        return failedConditions.isEmpty();
    }

    @Override
    public boolean isFailed() {
        return !isSuccessful();
    }

    @Override
    public void addFailedCondition(FailedCondition failedCondition) {
        failedConditions.add(failedCondition);
    }

    @Override
    public Set<FailedCondition> getFailedConditions(Object itemId) {
        return failedConditions
                .stream()
                .filter((FailedCondition condition) -> Objects.equals(itemId, condition.getItemId()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<FailedCondition> getFailedConditions() {
        return new LinkedHashSet<>(failedConditions);
    }

    @Override
    public String toString() {
        return "ConcurrentValidationResult{" +
                "failedConditions=" + failedConditions +
                '}';
    }
}
