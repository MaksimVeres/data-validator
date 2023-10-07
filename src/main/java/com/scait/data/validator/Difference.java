package com.scait.data.validator;

/**
 * Expectation difference.
 */
public enum Difference {

    /**
     * Case when the actual value should be less than expected.
     */
    LESS(-1),
    /**
     * Case when the actual value should be less or equal than expected.
     */
    LESS_OR_EQUAL(-1, 0),
    /**
     * Case when the actual value should be equal to expected.
     */
    EQUAL(0),
    /**
     * Case when the actual value should be greater or equal to expected.
     */
    GREATER_OR_EQUAL(0, 1),
    /**
     * Case when the actual value should be greater than expected.
     */
    GREATER(1);

    private final int[] expectedValues;

    /**
     * Difference constructor.
     *
     * @param expectedValues - expected comparator values
     */
    Difference(int... expectedValues) {
        this.expectedValues = expectedValues;
    }

    /**
     * Gets expected comparator values of Difference.
     *
     * @return - array of expected values
     */
    public int[] getExpectedValues() {
        return expectedValues;
    }
}
