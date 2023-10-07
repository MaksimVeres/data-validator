package com.scait.data.validator.impl;

import com.scait.data.validator.Difference;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Expectation for validation.
 *
 * @param <T> - Type of entity on validation
 * @param <R> - Type of entity value for validation
 */
public class Expectation<T, R> {

    private final Function<T, R> expectedValueProvider;
    private final Function<T, R> valueProvider;
    private Comparator<R> valueComparator;
    private Difference difference;

    private Expectation(Function<T, R> expectedValueProvider, Function<T, R> valueProvider) {
        this.expectedValueProvider = expectedValueProvider;
        this.valueProvider = valueProvider;
    }

    private Expectation(Function<T, R> expectedValueProvider, Function<T, R> valueProvider,
                        Comparator<R> valueComparator) {
        this.expectedValueProvider = expectedValueProvider;
        this.valueProvider = valueProvider;
        this.valueComparator = valueComparator;
        this.difference = Difference.EQUAL;
    }

    private Expectation(Function<T, R> expectedValueProvider, Function<T, R> valueProvider,
                        Comparator<R> valueComparator, Difference difference) {
        this.expectedValueProvider = expectedValueProvider;
        this.valueProvider = valueProvider;
        this.valueComparator = valueComparator;
        this.difference = difference;
    }

    /**
     * Gets actual value of a verification item by value provider.
     *
     * @param item - verification item
     * @return - actual value by value provider
     */
    public R getActualValue(T item) {
        return valueProvider.apply(item);
    }

    /**
     * Gets expected value provider.
     *
     * @return - value provider
     */
    public Function<T, R> getExpectedValueProvider() {
        return expectedValueProvider;
    }

    /**
     * Checks if an expectation is justified by verification item.
     * Compares expected and actual values according to defined expectation config.
     *
     * @param item - verification item
     * @return - true in case expectation is justified
     */
    public boolean isJustified(T item) {
        if (valueComparator == null) {
            return Objects.equals(getExpectedValueProvider().apply(item), getActualValue(item));
        }
        final var result = valueComparator.compare(getActualValue(item), expectedValueProvider.apply(item));
        for (int expectedValue : difference.getExpectedValues()) {
            if (result == expectedValue) {
                return true;
            }
        }
        return false;
    }

    /**
     * Constructs new expectation.
     *
     * @param expectedValue - expected value for verification
     * @param valueProvider - actual value provider
     * @param <T>           - Type of entity on verification
     * @param <R>           - Type of entity value for verification
     * @return - constructed expectation
     */
    public static <T, R> Expectation<T, R> of(Supplier<R> expectedValue, Function<T, R> valueProvider) {
        return new Expectation<>((T item) -> expectedValue.get(), valueProvider);
    }

    /**
     * Constructs new expectation.
     *
     * @param expectedValue   - expected value for verification
     * @param valueProvider   - actual value provider
     * @param valueComparator - comparator for values
     * @param <T>             - Type of entity on verification
     * @param <R>             - Type of entity value for verification
     * @return - constructed expectation
     */
    public static <T, R> Expectation<T, R> of(Supplier<R> expectedValue, Function<T, R> valueProvider,
                                              Comparator<R> valueComparator) {
        return new Expectation<>((T item) -> expectedValue.get(), valueProvider, valueComparator);
    }

    /**
     * Constructs new expectation.
     *
     * @param expectedValue   - expected value for verification
     * @param valueProvider   - actual value provider
     * @param valueComparator - comparator for values
     * @param difference      - expected difference for values
     * @param <T>             - Type of entity on verification
     * @param <R>             - Type of entity value for verification
     * @return - constructed expectation
     */
    public static <T, R> Expectation<T, R> of(Supplier<R> expectedValue, Function<T, R> valueProvider,
                                              Comparator<R> valueComparator, Difference difference) {
        return new Expectation<>((T item) -> expectedValue.get(), valueProvider, valueComparator, difference);
    }


    /**
     * Constructs new expectation.
     *
     * @param expectedValueProvider - provider of expected value for verification
     * @param valueProvider         - actual value provider
     * @param <T>                   - Type of entity on verification
     * @param <R>                   - Type of entity value for verification
     * @return - constructed expectation
     */
    public static <T, R> Expectation<T, R> of(
            Function<T, R> expectedValueProvider, Function<T, R> valueProvider) {
        return new Expectation<>(expectedValueProvider, valueProvider);
    }

    /**
     * Constructs new expectation.
     *
     * @param expectedValueProvider - provider of expected value for verification
     * @param valueProvider         - actual value provider
     * @param valueComparator       - comparator for values
     * @param <T>                   - Type of entity on verification
     * @param <R>                   - Type of entity value for verification
     * @return - constructed expectation
     */
    public static <T, R> Expectation<T, R> of(
            Function<T, R> expectedValueProvider, Function<T, R> valueProvider,
            Comparator<R> valueComparator) {
        return new Expectation<>(expectedValueProvider, valueProvider, valueComparator);
    }

    /**
     * Constructs new expectation.
     *
     * @param expectedValueProvider - provider of expected value for verification
     * @param valueProvider         - actual value provider
     * @param valueComparator       - comparator for values
     * @param difference            - expected difference for values
     * @param <T>                   - Type of entity on verification
     * @param <R>                   - Type of entity value for verification
     * @return - constructed expectation
     */
    public static <T, R> Expectation<T, R> of(
            Function<T, R> expectedValueProvider, Function<T, R> valueProvider,
            Comparator<R> valueComparator, Difference difference) {
        return new Expectation<>(expectedValueProvider, valueProvider, valueComparator, difference);
    }
}
