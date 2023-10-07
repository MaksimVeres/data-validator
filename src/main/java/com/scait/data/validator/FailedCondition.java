package com.scait.data.validator;

/**
 * Failed validation condition.
 */
public interface FailedCondition {

    /**
     * Gets an item id of item that failed verification.
     *
     * @return - item id
     */
    Object getItemId();

    /**
     * Gets expected value of verification.
     *
     * @return - expected value
     */
    Object getExpectedValue();

    /**
     * Gets actual value of verification.
     *
     * @return - actual value
     */
    Object getActualValue();
}
