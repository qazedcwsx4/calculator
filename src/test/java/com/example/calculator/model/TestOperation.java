package com.example.calculator.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.example.calculator.model.DistanceUnit.FOOT;
import static com.example.calculator.model.DistanceUnit.METER;
import static com.example.calculator.model.DistanceUnit.NAUTICAL_MILE;
import static com.example.calculator.model.UnitConstants.NAUTICAL_MILES_TO_METERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestOperation {

    private static final double NM_MINUS_FOOT_IN_NM = 0.999835421;
    private static final double NM_PLUS_FOOT_IN_NM = 1.00016458;

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testOperationCases")
    public void testConversion(String name, Operation operation, Distance arg1, Distance arg2, Distance expected) {
        Distance actual = operation.apply(arg1, arg2);

        assertThat(actual.getAmount()).isEqualTo(expected.getAmount(), byLessThan(0.001));
        assertThat(actual.getExponent()).isEqualTo(expected.getExponent());
        assertThat(actual.getUnit()).isEqualTo(expected.getUnit());
    }

    @Test
    public void additionOfDifferentExponentsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Operation.ADD.apply(Distance.of(1, METER, 1), Distance.of(1, METER, 2));
        });
    }

    @Test
    public void subtractionOfDifferentExponentsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Operation.SUBTRACT.apply(Distance.of(1, METER, 1), Distance.of(1, METER, 2));
        });
    }

    @Test
    public void divisionByZeroReturnsInfinity() {
        Distance actual = Operation.DIVIDE.apply(Distance.of(1, METER), Distance.of(0, METER));

        assertThat(Double.isInfinite(actual.getAmount())).isTrue();
        assertThat(actual.getExponent()).isEqualTo(0);
        assertThat(actual.getUnit()).isEqualTo(METER);
    }

    public static Stream<Arguments> testOperationCases() {
        return Stream.of(
                Arguments.of(
                        "1 meter + 1 meter",
                        Operation.ADD,
                        Distance.of(1, METER),
                        Distance.of(1, METER),
                        Distance.of(2, METER)
                ), Arguments.of(
                        "1 meter - 1 meter",
                        Operation.SUBTRACT,
                        Distance.of(1, METER),
                        Distance.of(1, METER),
                        Distance.of(0, METER)
                ), Arguments.of(
                        "1 nautical mile + 1 feet",
                        Operation.ADD,
                        Distance.of(1, NAUTICAL_MILE),
                        Distance.of(1, FOOT),
                        Distance.of(NM_PLUS_FOOT_IN_NM, NAUTICAL_MILE)
                ), Arguments.of(
                        "1 nautical mile - 1 feet",
                        Operation.SUBTRACT,
                        Distance.of(1, NAUTICAL_MILE),
                        Distance.of(1, FOOT),
                        Distance.of(NM_MINUS_FOOT_IN_NM, NAUTICAL_MILE)
                ), Arguments.of(
                        "-11 meters + 123 meters",
                        Operation.ADD,
                        Distance.of(-11, METER),
                        Distance.of(123, METER),
                        Distance.of(123 - 11, METER)
                ), Arguments.of(
                        "1 meter / 1 nautical mile",
                        Operation.DIVIDE,
                        Distance.of(1, METER, 1),
                        Distance.of(1, NAUTICAL_MILE, 1),
                        Distance.of(1 / NAUTICAL_MILES_TO_METERS, METER, 0)
                ), Arguments.of(
                        "1 meter * 1 nautical mile",
                        Operation.MULTIPLY,
                        Distance.of(1, METER, 1),
                        Distance.of(1, NAUTICAL_MILE, 1),
                        Distance.of(NAUTICAL_MILES_TO_METERS, METER, 2)
                )
        );
    }
}
