package dragon;

import java.io.Serializable;
import java.util.Objects;

/**
 * A class describing a dragon killer
 */
public class Person implements Serializable {
    private String name;
    private Float height;
    private long weight;
    private Location location;

    public Person(String name, Float height, long weight, Location location) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Float getHeight() {
        return height;
    }

    public long getWeight() {
        return weight;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name = '" + name + '\'' +
                ", height = " + height +
                ", weight = " + weight +
                ", location = " + location +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return weight == person.weight &&
                Objects.equals(name, person.name) &&
                Objects.equals(height, person.height) &&
                Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, height, weight, location);
    }
}