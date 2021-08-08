package com.example.calculator.model;

import java.util.Locale;
import java.util.Objects;

public class Distance {
    private final double amount;

    private final DistanceUnit unit;

    private final int exponent;

    protected Distance(double amount, DistanceUnit unit, int exponent) {
        this.amount = amount;
        this.unit = unit;
        this.exponent = exponent;
    }

    public final double getAmount() {
        return amount;
    }

    public final DistanceUnit getUnit() {
        return unit;
    }

    public final int getExponent() {
        return exponent;
    }

    /**
     * Returns a new instance of {@link Distance} converted to another {@link DistanceUnit}
     *
     * @param unit {@link DistanceUnit} of the new instance
     * @return converted new instance of {@link Distance}
     */
    public final Distance to(DistanceUnit unit) {
        double newAmount = this.amount * Math.pow(DistanceUnit.getRatio(this.unit, unit), exponent);

        return Distance.of(newAmount, unit, exponent);
    }

    /**
     * Creates a new instance of {@link Distance} using the {@link DistanceUnit} and its amount,
     * defaulting the unit's exponent to 1
     *
     * @param amount       amount of {@link DistanceUnit}
     * @param distanceUnit {@link DistanceUnit} of the instance
     * @return a new instance of {@link Distance} from {@link DistanceUnit} and its amount
     */
    public static Distance of(double amount, DistanceUnit distanceUnit) {
        return new Distance(amount, distanceUnit, 1);
    }

    /**
     * Creates a new instance of {@link Distance} using the {@link DistanceUnit}, its amount and its exponent
     *
     * @param amount       amount of {@link DistanceUnit}
     * @param distanceUnit {@link DistanceUnit} of the instance
     * @param exponent     the exponent of the instance's unit
     * @return a new instance of {@link Distance} from {@link DistanceUnit}, its amount and its exponent
     */
    public static Distance of(double amount, DistanceUnit distanceUnit, int exponent) {
        return new Distance(amount, distanceUnit, exponent);
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%.2f", amount) +
                (exponent == 0 ? "" : unit.toString()) +
                (exponent == 1 || exponent == 0 ? "" : "^" + exponent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distance distance = (Distance) o;
        return Double.compare(distance.amount, amount) == 0 && exponent == distance.exponent && unit == distance.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, unit, exponent);
    }
}
