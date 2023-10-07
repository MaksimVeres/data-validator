package com.scait.data.validator.impl;

import com.scait.data.validator.FailedCondition;
import com.scait.data.validator.ValidationResult;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ValidationResult} class.
 */
class DataValidationResult implements ValidationResult {

    private final Set<FailedCondition> failedConditions = new LinkedHashSet<>();

    /**
     * Default constructor.
     */
    public DataValidationResult() {
        //No op.
    }

    public void addFailedCondition(FailedCondition failedCondition) {
        failedConditions.add(failedCondition);
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
    public Set<FailedCondition> getFailedConditions(Object itemId) {
        return failedConditions.stream()
                .filter((FailedCondition condition) -> Objects.equals(itemId, condition.getItemId()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<FailedCondition> getFailedConditions() {
        return new LinkedHashSet<>(failedConditions);
    }

    @Override
    public String toString() {
        return "VerifierValidationResult{" +
                "failedConditions=" + failedConditions +
                '}';
    }
}
