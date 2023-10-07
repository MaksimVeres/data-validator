package com.scait.data.validator.impl;

import com.scait.data.validator.ValidationResult;
import com.scait.data.validator.Validator;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Implementation of {@link Validator} class.
 *
 * @param <T> - type of entity for validation
 */
public class DataValidator<T> implements Validator<T> {

    private final List<Stream<T>> acceptedData = new LinkedList<>();
    private final List<Expectation<T, ?>> expectations = new LinkedList<>();
    private Function<T, ?> idProvider = (T item) -> item;
    private boolean parallel;

    /**
     * Default constructor.
     */
    public DataValidator() {
        //No op.
    }

    @Override
    public Validator<T> accept(Stream<T> data) {
        acceptedData.add(data);
        return this;
    }

    @Override
    public <R> Validator<T> setItemId(Function<T, R> idProvider) {
        this.idProvider = idProvider;
        return this;
    }

    @Override
    public Validator<T> expect(Expectation<T, ?> expectation) {
        expectations.add(expectation);
        return this;
    }

    @Override
    public Validator<T> parallel() {
        parallel = true;
        return this;
    }

    @Override
    public ValidationResult validate() {
        if (parallel) {
            return validateParallel();
        }
        return validateSequential();
    }

    private ValidationResult validateSequential() {
        final var validationResult = new DataValidationResult();
        acceptedData
                .stream()
                .flatMap((Stream<T> dataStream) -> dataStream)
                .forEachOrdered((T data) -> validateData(data, validationResult));
        return validationResult;
    }

    private ValidationResult validateParallel() {
        final var validationResult = new ConcurrentDataValidationResult();
        acceptedData
                .stream()
                .flatMap((Stream<T> dataStream) -> dataStream)
                .parallel()
                .forEach((T data) -> validateData(data, validationResult));
        return validationResult;
    }

    private void validateData(T data, ValidationResult validationResult) {
        for (Expectation<T, ?> expectation : expectations) {
            if (!expectation.isJustified(data)) {
                validationResult.addFailedCondition(new FailedRecord<>(
                        idProvider.apply(data),
                        expectation.getExpectedValueProvider().apply(data),
                        expectation.getActualValue(data)
                ));
            }
        }
    }
}
