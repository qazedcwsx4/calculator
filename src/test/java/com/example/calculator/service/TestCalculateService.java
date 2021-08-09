package com.example.calculator.service;

import com.example.calculator.model.Distance;
import com.example.calculator.model.Operation;
import com.example.calculator.rest.CalculateRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static com.example.calculator.model.DistanceUnit.FOOT;
import static com.example.calculator.model.DistanceUnit.METER;
import static com.example.calculator.model.DistanceUnit.NAUTICAL_MILE;
import static com.example.calculator.model.UnitConstants.FEET_TO_METERS;
import static com.example.calculator.model.UnitConstants.NAUTICAL_MILES_TO_METERS;
import static java.lang.Math.pow;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;

@SpringBootTest
class TestCalculateService {

    @Autowired
    CalculateService calculateService;

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testCalculationParsingCases")
    void testCalculate(CalculateRequest request, Distance expected) {
        Distance actual = calculateService.calculate(request);

        assertThat(actual.getAmount()).isEqualTo(expected.getAmount(), byLessThan(0.001));
        assertThat(actual.getExponent()).isEqualTo(expected.getExponent());
        assertThat(actual.getUnit()).isEqualTo(expected.getUnit());
    }

    public static Stream<Arguments> testCalculationParsingCases() {
        return Stream.of(
                Arguments.of(new CalculateRequest(
                                Distance.of(1, METER),
                                Distance.of(1, METER),
                                Operation.ADD, METER),
                        Distance.of(2, METER)
                ), Arguments.of(new CalculateRequest(
                                Distance.of(1, METER),
                                Distance.of(1, METER),
                                Operation.SUBTRACT, METER),
                        Distance.of(0, METER)
                ), Arguments.of(new CalculateRequest(
                                Distance.of(1, METER),
                                Distance.of(1, METER),
                                Operation.DIVIDE, METER),
                        Distance.of(1, METER, 0)
                ), Arguments.of(new CalculateRequest(
                                Distance.of(1, METER),
                                Distance.of(1, METER),
                                Operation.MULTIPLY, METER),
                        Distance.of(1, METER, 2)
                ), Arguments.of(new CalculateRequest(
                                Distance.of(1, FOOT),
                                Distance.of(1, NAUTICAL_MILE),
                                Operation.ADD, METER),
                        Distance.of(NAUTICAL_MILES_TO_METERS + FEET_TO_METERS, METER, 1)
                ), Arguments.of(new CalculateRequest(
                                Distance.of(1, FOOT, 2),
                                Distance.of(1, NAUTICAL_MILE, 2),
                                Operation.ADD, METER),
                        Distance.of(pow(NAUTICAL_MILES_TO_METERS, 2) + pow(FEET_TO_METERS, 2), METER, 2)
                )
        );
    }
}
