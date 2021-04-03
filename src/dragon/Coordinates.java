package dragon;

import java.io.Serializable;
import java.util.Objects;

/**
 * Coordinates of dragon
 */
public class Coordinates implements Serializable {
    private Long x;
    private double y;

    public Coordinates(Long x, double y) {
        this.x = x;
        this.y = y;
    }

    public Long getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.y, y) == 0 &&
                Objects.equals(x, that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
