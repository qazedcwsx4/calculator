package com.example.calculator.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.example.calculator.model.DistanceUnit.FOOT;
import static com.example.calculator.model.DistanceUnit.METER;
import static com.example.calculator.model.DistanceUnit.NAUTICAL_MILE;
import static com.example.calculator.model.UnitConstants.FEET_TO_METERS;
import static com.example.calculator.model.UnitConstants.NAUTICAL_MILES_TO_METERS;
import static java.lang.Math.pow;
import static org.assertj.core.api.Assertions.assertThat;

class TestDistance {

    @ParameterizedTest(name = "{index}: {0} to {1}")
    @MethodSource("distanceToConversionCases")
    public void testDistanceToAnotherUnit(Distance distance, DistanceUnit toUnit, Distance expected) {
        assertThat(distance.to(toUnit)).isEqualTo(expected);
    }

    public static Stream<Arguments> distanceToConversionCases() {
        return Stream.of(
                Arguments.of(Distance.of(1, METER), METER, Distance.of(1, METER)),
                Arguments.of(Distance.of(1, NAUTICAL_MILE), METER, Distance.of(NAUTICAL_MILES_TO_METERS, METER)),
                Arguments.of(Distance.of(1, FOOT), METER, Distance.of(FEET_TO_METERS, METER)),
                Arguments.of(Distance.of(NAUTICAL_MILES_TO_METERS, METER), NAUTICAL_MILE, Distance.of(1, NAUTICAL_MILE)),
                Arguments.of(Distance.of(NAUTICAL_MILES_TO_METERS, METER), NAUTICAL_MILE, Distance.of(1, NAUTICAL_MILE)),

                Arguments.of(
                        Distance.of(1, NAUTICAL_MILE, 2), METER,
                        Distance.of(pow(NAUTICAL_MILES_TO_METERS, 2), METER, 2)
                ), Arguments.of(
                        Distance.of(1, NAUTICAL_MILE, 3), METER,
                        Distance.of(pow(NAUTICAL_MILES_TO_METERS, 3), METER, 3)
                ), Arguments.of(
                        Distance.of(1, NAUTICAL_MILE, 0), METER,
                        Distance.of(1, METER, 0)
                ), Arguments.of(
                        Distance.of(1, NAUTICAL_MILE, -1), METER,
                        Distance.of(pow(NAUTICAL_MILES_TO_METERS, -1), METER, -1)
                )
        );
    }

    @ParameterizedTest(name = "{index}: {1}")
    @MethodSource("distanceToStringCases")
    public void testDistanceToString(Distance distance, String expected) {
        assertThat(distance.toString()).isEqualTo(expected);
    }

    public static Stream<Arguments> distanceToStringCases() {
        return Stream.of(
                Arguments.of(Distance.of(1, METER), "1.00m"),
                Arguments.of(Distance.of(1, METER), "1.00m"),
                Arguments.of(Distance.of(1234, METER), "1234.00m"),
                Arguments.of(Distance.of(1.1234, METER), "1.12m"),
                Arguments.of(Distance.of(1.129, METER), "1.13m"),
                Arguments.of(Distance.of(1, METER, 2), "1.00m^2"),
                Arguments.of(Distance.of(1, METER, -1), "1.00m^-1")
        );
    }
}