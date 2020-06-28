package expressionevaluator.tokens;

import java.util.Objects;

/**
 * Represents a mathematical real number.
 */
public class RealNumber implements Token {

    private final double value;

    private static final double EPSILON = 0.00001;

    public RealNumber(double value) {
        this.value = value;
    }

    public RealNumber(String value) {
        this.value = Double.parseDouble(value);
    }

    /**
     * Returns the value of this real number.
     *
     * @return the value of this real number
     */
    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealNumber that = (RealNumber) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return (Math.abs(this.value % 1) < EPSILON)
                ? Math.round(this.value) + ""
                : this.value + "";
    }
}
