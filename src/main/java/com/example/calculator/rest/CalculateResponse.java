package com.example.calculator.rest;

import com.example.calculator.model.Distance;

import java.util.Objects;
import java.util.StringJoiner;

public class CalculateResponse {

    private final String stringRepresentation;

    private final Distance distance;

    // region Constructor, getters, equals, hashCode, toString -- generated by BoB the Builder of Beans
    // The code below has been generated by BoB the Builder of Beans based on the class' fields.
    // Everything after this comment will be regenerated if you invoke BoB again.
    // If you don't know who BoB is, you can find him here: https://bitbucket.org/atlassianlabs/bob-the-builder-of-beans

    public CalculateResponse(String stringRepresentation, Distance distance) {
        this.stringRepresentation = Objects.requireNonNull(stringRepresentation);
        this.distance = Objects.requireNonNull(distance);
    }

    // region Getters -- generated by BoB the Builder of Beans
    public String getStringRepresentation() {
        return stringRepresentation;
    }

    public Distance getDistance() {
        return distance;
    }// endregion Getters

    // region hashCode() and equals() -- generated by BoB the Builder of Beans
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CalculateResponse that = (CalculateResponse) o;

        return Objects.equals(this.getStringRepresentation(), that.getStringRepresentation()) && Objects.equals(this.getDistance(), that.getDistance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStringRepresentation(), getDistance());
    }// endregion hashCode() and equals()

    // region toString() -- generated by BoB the Builder of Beans
    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "{", "}")
                .add("stringRepresentation=" + getStringRepresentation())
                .add("distance=" + getDistance())
                .toString();
    }// endregion toString()
    // endregion Constructor, getters, equals, hashCode, toString
}
