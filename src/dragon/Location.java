package dragon;

import java.io.Serializable;
import java.util.Objects;

/**
 * Location of a dragon killer
 */
public class Location implements Serializable {
    private float x;
    private int y;
    private Float z;
    private String name;

    public Location(float x, int y, Float z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Float getZ() {
        return z;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", name = '" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Float.compare(location.x, x) == 0 &&
                y == location.y &&
                Objects.equals(z, location.z) &&
                Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }
}