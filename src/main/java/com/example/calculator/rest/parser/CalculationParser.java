package com.example.calculator.rest.parser;

import com.example.calculator.model.Distance;
import com.example.calculator.model.DistanceUnit;
import com.example.calculator.model.Operation;
import com.example.calculator.rest.CalculateRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculationParser {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
    private static final Pattern UNIT_PATTERN = Pattern.compile("[a-zA-Z]+");
    private static final Pattern EXPONENT_PATTERN = Pattern.compile("\\^-?[0-9]+");
    private static final Pattern OPERATION_PATTERN = Pattern.compile("[-+*/]");
    private static final Pattern EQUALS_PATTERN = Pattern.compile("=");

    private final Matcher NUMBER_MATCHER;
    private final Matcher UNIT_MATCHER;
    private final Matcher EXPONENT_MATCHER;
    private final Matcher OPERATION_MATCHER;
    private final Matcher EQUALS_MATCHER;

    private Matcher lastMatcher = null;

    public CalculationParser(String request) {
        this.NUMBER_MATCHER = NUMBER_PATTERN.matcher(request);
        this.UNIT_MATCHER = UNIT_PATTERN.matcher(request);
        this.EXPONENT_MATCHER = EXPONENT_PATTERN.matcher(request);
        this.OPERATION_MATCHER = OPERATION_PATTERN.matcher(request);
        this.EQUALS_MATCHER = EQUALS_PATTERN.matcher(request);
    }

    public CalculateRequest parse() {
        Distance distance1 = getDistance();
        Operation operation = Operation.from(getMatchedToken(OPERATION_MATCHER));
        Distance distance2 = getDistance();
        if (!getMatchedToken(EQUALS_MATCHER).equals("=")) throw new IllegalArgumentException();
        DistanceUnit resultUnit = DistanceUnit.from(getMatchedToken(UNIT_MATCHER));

        return new CalculateRequest(distance1, distance2, operation, resultUnit);
    }

    private Distance getDistance() {
        return Distance.of(
                Double.parseDouble(getMatchedToken(NUMBER_MATCHER)),
                DistanceUnit.from(getMatchedToken(UNIT_MATCHER)),
                getExponent()
        );
    }

    private String getMatchedToken(Matcher tokenMatcher) {
        int lastTokenEnd = lastMatcher != null ? lastMatcher.end() : 0;
        if (!tokenMatcher.find(lastTokenEnd) || tokenMatcher.start() != lastTokenEnd)
            throw new IllegalArgumentException();

        lastMatcher = tokenMatcher;

        return tokenMatcher.group();
    }

    private int getExponent() {
        Matcher tokenMatcher = EXPONENT_MATCHER;
        int lastTokenEnd = lastMatcher.end();

        boolean found = tokenMatcher.find(lastTokenEnd);
        if (!found || tokenMatcher.start() != lastTokenEnd) {
            return 1;
        }

        lastMatcher = tokenMatcher;

        String exponent = tokenMatcher.group();
        if (exponent.charAt(0) != '^') throw new IllegalArgumentException();
        return Integer.parseInt(exponent.substring(1));
    }
}
