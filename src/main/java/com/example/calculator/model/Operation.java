package com.example.calculator.model;

import java.util.EnumSet;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;

public enum Operation implements BinaryOperator<Distance> {
    ADD         ((a, b) -> a + b, ExponentChange.NONE, "+"),
    SUBTRACT    ((a, b) -> a - b, ExponentChange.NONE, "-"),
    DIVIDE      ((a, b) -> a / b, ExponentChange.DECREMENT, "|"),
    MULTIPLY    ((a, b) -> a * b, ExponentChange.INCREMENT, "*");

    private final DoubleBinaryOperator operator;

    private final IntBinaryOperator exponentChange;

    private final String symbol;

    private static final Map<String, Operation> operationSymbols =
            EnumSet.allOf(Operation.class).stream().collect(Collectors.toMap(it -> it.symbol, it -> it));

    Operation(DoubleBinaryOperator operator, IntBinaryOperator exponentChange, String symbol) {
        this.operator = operator;
        this.exponentChange = exponentChange;
        this.symbol = symbol;
    }

    @Override
    public Distance apply(Distance a, Distance b) {
        DistanceUnit unit = a.getUnit();

        double convertedAmount = operator.applyAsDouble(a.getAmount(), b.to(unit).getAmount());
        int updatedExponent = exponentChange.applyAsInt(a.getExponent(), b.getExponent());

        return Distance.of(convertedAmount, unit, updatedExponent);
    }

    /**
     * Returns an instance from String representation
     *
     * @param value a string representation of Operation or the symbol
     * @return Operation instance
     * @throws IllegalArgumentException when a corresponding instance cannot be found
     */
    public static Operation from(String value) {
        Operation fromSymbol = operationSymbols.get(value);
        return fromSymbol != null ? fromSymbol : valueOf(value.toUpperCase());
    }

    @Override
    public String toString() {
        return symbol;
    }

    private enum ExponentChange implements IntBinaryOperator {
        NONE((p1, p2) -> p1, (p1, p2) -> {
            if (!p1.equals(p2)) throw new IllegalArgumentException();
        }),
        INCREMENT((p1, p2) -> p1 + p2),
        DECREMENT((p1, p2) -> p1 - p2);

        private final IntBinaryOperator change;

        private final BiConsumer<Integer, Integer> validator;

        ExponentChange(IntBinaryOperator change) {
            this.change = change;
            this.validator = (a, b) -> {};
        }

        ExponentChange(IntBinaryOperator change, BiConsumer<Integer, Integer> validator) {
            this.change = change;
            this.validator = validator;
        }

        @Override
        public int applyAsInt(int left, int right) {
            validator.accept(left, right);

            return change.applyAsInt(left, left);
        }
    }
}
