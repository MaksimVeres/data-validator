package com.scait.data.validator.impl;

import com.scait.data.validator.Difference;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.scait.data.validator.impl.Expectation.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link DataValidator} class.
 */
class DataValidatorTest {

    private static final String ID_1 = "ID_1";
    private static final String ID_2 = "ID_2";
    private static final String NAME_1 = "NAME_1";
    private static final String NAME_2 = "NAME_2";
    private static final int VALUE_1 = 1;
    private static final int VALUE_2 = 2;
    private static final String ITEM_1 = "ITEM_1";
    private static final String ITEM_2 = "ITEM_2";

    @Test
    void shouldValidateItems() {
        final var object1 = obj(ID_1, NAME_1, VALUE_1, List.of(ITEM_1));
        final var object2 = obj(ID_2, NAME_2, VALUE_2, List.of(ITEM_1, ITEM_2));

        final DataValidator<TestObject> dataVerifier = new DataValidator<>();
        final var validationResult = dataVerifier
                .accept(Stream.of(object1, object2))
                .setItemId(TestObject::getId)
                .expect(of(() -> 4, (TestObject testObj) -> testObj.getId().length()))
                .expect(of(TestObject::getId, TestObject::getName))
                .expect(of(() -> 2, TestObject::getValue, Integer::compareTo, Difference.GREATER_OR_EQUAL))
                .expect(of(TestObject::getItems, TestObject::getItems))
                .validate();
        final var failedConditions1 = validationResult.getFailedConditions(ID_1);
        final var failedConditions2 = validationResult.getFailedConditions(ID_2);

        assertTrue(validationResult.isFailed());
        assertEquals(2, failedConditions1.size());
        assertEquals(1, failedConditions2.size());
    }

    @Test
    void shouldValidateItemsInParallel() {
        final var object1 = obj(ID_1, NAME_1, VALUE_1, List.of(ITEM_1));
        final var object2 = obj(ID_2, NAME_2, VALUE_2, List.of(ITEM_1, ITEM_2));

        final DataValidator<TestObject> dataVerifier = new DataValidator<>();
        final var validationResult = dataVerifier
                .accept(Stream.of(object1, object2))
                .setItemId(TestObject::getId)
                .expect(of(() -> 4, (TestObject testObj) -> testObj.getId().length()))
                .expect(of(TestObject::getId, TestObject::getName))
                .expect(of(() -> 2, TestObject::getValue, Integer::compareTo, Difference.GREATER_OR_EQUAL))
                .expect(of(TestObject::getItems, TestObject::getItems))
                .parallel()
                .validate();
        final var failedConditions1 = validationResult.getFailedConditions(ID_1);
        final var failedConditions2 = validationResult.getFailedConditions(ID_2);

        assertTrue(validationResult.isFailed());
        assertEquals(2, failedConditions1.size());
        assertEquals(1, failedConditions2.size());
    }

    private TestObject obj(String id, String name, int value, Collection<Object> items) {
        return new TestObject(id, name, value, items);
    }

    /**
     * Mock data object for verifier testing.
     */
    private static class TestObject {

        private final String id;
        private final String name;
        private final int value;
        private final Collection<Object> items;

        /**
         * Parametrized constructor.
         *
         * @param id    - id
         * @param name  - name
         * @param value - value
         * @param items - items
         */
        public TestObject(String id, String name, int value, Collection<Object> items) {
            this.id = id;
            this.name = name;
            this.value = value;
            this.items = items;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public Collection<Object> getItems() {
            return items;
        }
    }
}