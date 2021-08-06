package com.example.calculator.model;

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

public class TestDistanceUnit {

    private static final double NAUTICAL_MILES_TO_FOOT = 6076.11549;

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("conversionTestCases")
    public void testConversion(String name, DistanceUnit ratioOf, DistanceUnit ratioTo, double expected) {
        assertThat(DistanceUnit.getRatio(ratioOf, ratioTo)).isEqualTo(expected, byLessThan(0.001));
    }

    public static Stream<Arguments> conversionTestCases() {
        return Stream.of(
                Arguments.of("nautical mile to meter", NAUTICAL_MILE, METER, NAUTICAL_MILES_TO_METERS),
                Arguments.of("meter to nautical mile", METER, NAUTICAL_MILE, 1 / NAUTICAL_MILES_TO_METERS),
                Arguments.of("nautical mile to foot", NAUTICAL_MILE, FOOT, NAUTICAL_MILES_TO_FOOT),
                Arguments.of("foot to nautical mile", FOOT, NAUTICAL_MILE, 1 / NAUTICAL_MILES_TO_FOOT)
        );
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testDistanceUnitParsingCases")
    public void testDistanceUnitParsing(String name, DistanceUnit expected) {
        assertThat(DistanceUnit.from(name)).isEqualTo(expected);
    }

    public static Stream<Arguments> testDistanceUnitParsingCases() {
        return Stream.of(
                Arguments.of("meter", METER),
                Arguments.of("MeTeR", METER),
                Arguments.of("METER", METER),
                Arguments.of("m", METER),
                Arguments.of("nm", NAUTICAL_MILE)
        );
    }
}
