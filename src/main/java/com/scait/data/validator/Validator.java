package com.scait.data.validator;

import com.scait.data.validator.impl.Expectation;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Interface that helps to build validation steps in one place.
 *
 * @param <T> - Type of entity to be validated
 */
public interface Validator<T> {

    /**
     * Accepts stream of data for verification.
     *
     * @param data - stream of data for verification
     * @return - verifier instance
     */
    Validator<T> accept(Stream<T> data);

    /**
     * Sets an entity item id.
     * Using entity self object by default.
     *
     * @param idProvider - id provider function
     * @param <R>        - result ID type
     * @return - verifier instance
     */
    <R> Validator<T> setItemId(Function<T, R> idProvider);

    /**
     * Adds verification expectation.
     *
     * @param expectation - expectation for verification
     * @return - verifier instance
     */
    Validator<T> expect(Expectation<T, ?> expectation);

    /**
     * Sets data verification parallel mode enabled.
     *
     * @return - verifier instance
     */
    Validator<T> parallel();

    /**
     * Starts verification of all accepted data and creates a validation result.
     *
     * @return - verification validation result
     */
    ValidationResult validate();
}
