package com.scait.data.validator;

import java.util.Set;

/**
 * Verification validation result.
 */
public interface ValidationResult {

    /**
     * Defines whether validation was successful.
     *
     * @return - true in case all verifications are passed; false otherwise
     */
    boolean isSuccessful();

    /**
     * Defines whether validation has failed items.
     *
     * @return - true in case there is any of items that failed validation
     */
    boolean isFailed();

    /**
     * Adds failed condition to the validation result.
     *
     * @param failedCondition - failed condition item
     */
    void addFailedCondition(FailedCondition failedCondition);

    /**
     * Gets failed conditions of the specific item by item id.
     *
     * @param itemId - id of the item to get failed conditions
     * @return - Set of FailedCondition
     */
    Set<FailedCondition> getFailedConditions(Object itemId);

    /**
     * Gets all failed conditions of all items.
     *
     * @return - Set of FailedCondition
     */
    Set<FailedCondition> getFailedConditions();
}
