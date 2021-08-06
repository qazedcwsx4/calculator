package com.example.calculator.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.calculator.model.UnitConstants.FEET_TO_METERS;
import static com.example.calculator.model.UnitConstants.NAUTICAL_MILES_TO_METERS;

/**
 * Represents a unit of distance
 */
public enum DistanceUnit {
    METER           (1.0, "m"),
    FOOT            (FEET_TO_METERS, "ft"),
    NAUTICAL_MILE   (NAUTICAL_MILES_TO_METERS, "nm");

    private final double ratioToMeter;

    private final String abbreviation;

    private static final Map<String, DistanceUnit> distanceUnitAbbreviations =
            EnumSet.allOf(DistanceUnit.class).stream().collect(Collectors.toMap(it -> it.abbreviation, it -> it));

    DistanceUnit(double ratioToMeter, String abbreviation) {
        this.ratioToMeter = ratioToMeter;
        this.abbreviation = abbreviation;
    }

    /**
     * Calculates the ratio of two distance units.
     * Operates on logic that "ratio of A to B" is A/B
     *
     * @param of First unit of the ratio
     * @param to Second unit of the ratio
     * @return the ratio of the first parameter to the second
     */
    public static double getRatio(DistanceUnit of, DistanceUnit to) {
        return of.ratioToMeter / to.ratioToMeter;
    }

    /**
     * Returns an instance from String representation
     *
     * @param value a string representation of DistanceUnit, either the name of abbreviation
     * @return DistanceUnit instance
     * @throws IllegalArgumentException when a corresponding instance cannot be found
     */
    @JsonCreator
    public static DistanceUnit from(String value) {
        DistanceUnit fromAbbreviation = distanceUnitAbbreviations.get(value.toLowerCase());

        return fromAbbreviation != null ? fromAbbreviation : valueOf(value.toUpperCase());
    }

    @Override
    public String toString() {
        return abbreviation;
    }
}
