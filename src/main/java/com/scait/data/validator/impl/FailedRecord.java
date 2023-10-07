package com.scait.data.validator.impl;

import com.scait.data.validator.FailedCondition;

/**
 * Implementation of {@link  FailedCondition} class.
 *
 * @param <I> - item id type
 * @param <T> - validation entity type
 */
public class FailedRecord<I, T> implements FailedCondition {

    private final I itemId;
    private final T expectedValue;
    private final T actualValue;

    /**
     * Parametrized constructor.
     *
     * @param itemId        - id of the verification item
     * @param expectedValue - expected value
     * @param actualValue   - actual value
     */
    public FailedRecord(I itemId, T expectedValue, T actualValue) {
        this.itemId = itemId;
        this.expectedValue = expectedValue;
        this.actualValue = actualValue;
    }

    @Override
    public I getItemId() {
        return itemId;
    }

    @Override
    public T getExpectedValue() {
        return expectedValue;
    }

    @Override
    public T getActualValue() {
        return actualValue;
    }

    @Override
    public String toString() {
        return "FailedRecord{" +
                "itemId=" + itemId +
                ", expectedValue=" + expectedValue +
                ", actualValue=" + actualValue +
                '}';
    }
}
