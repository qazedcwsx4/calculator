package com.example.calculator.rest.parser;

import com.example.calculator.model.Distance;
import com.example.calculator.model.Operation;
import com.example.calculator.rest.CalculateRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.example.calculator.model.DistanceUnit.FOOT;
import static com.example.calculator.model.DistanceUnit.METER;
import static com.example.calculator.model.DistanceUnit.NAUTICAL_MILE;
import static org.assertj.core.api.Assertions.assertThat;

class TestCalculationParser {

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testCalculationParsingCases")
    void testCalculationParsing(String calculation, CalculateRequest expected) {
        assertThat(new CalculationParser(calculation).parse()).isEqualTo(expected);
    }

    public static Stream<Arguments> testCalculationParsingCases() {
        return Stream.of(
                Arguments.of("1m+1m=m", new CalculateRequest(
                        Distance.of(1, METER),
                        Distance.of(1, METER),
                        Operation.ADD,
                        METER)
                ), Arguments.of("1m-1m=m", new CalculateRequest(
                        Distance.of(1, METER),
                        Distance.of(1, METER),
                        Operation.SUBTRACT,
                        METER)
                ), Arguments.of("1m/1m=m", new CalculateRequest(
                        Distance.of(1, METER),
                        Distance.of(1, METER),
                        Operation.DIVIDE,
                        METER)
                ), Arguments.of("1m*1m=m", new CalculateRequest(
                        Distance.of(1, METER),
                        Distance.of(1, METER),
                        Operation.MULTIPLY,
                        METER)
                ), Arguments.of("123m^12+456m^-3=m", new CalculateRequest(
                        Distance.of(123, METER, 12),
                        Distance.of(456, METER, -3),
                        Operation.ADD,
                        METER)
                ), Arguments.of("123m^2+456m=m", new CalculateRequest(
                        Distance.of(123, METER, 2),
                        Distance.of(456, METER),
                        Operation.ADD,
                        METER)
                ), Arguments.of("123m+456m^2=m", new CalculateRequest(
                        Distance.of(123, METER),
                        Distance.of(456, METER, 2),
                        Operation.ADD,
                        METER)
                ), Arguments.of("1ft+1m=nm", new CalculateRequest(
                        Distance.of(1, FOOT),
                        Distance.of(1, METER),
                        Operation.ADD,
                        NAUTICAL_MILE)
                ), Arguments.of("-1m+-1m=m", new CalculateRequest(
                        Distance.of(-1, METER),
                        Distance.of(-1, METER),
                        Operation.ADD,
                        METER)
                ), Arguments.of("-1m+1m=m", new CalculateRequest(
                        Distance.of(-1, METER),
                        Distance.of(1, METER),
                        Operation.ADD,
                        METER)
                ), Arguments.of("1m+-1m=m", new CalculateRequest(
                        Distance.of(1, METER),
                        Distance.of(-1, METER),
                        Operation.ADD,
                        METER)
                )
        );
    }
}