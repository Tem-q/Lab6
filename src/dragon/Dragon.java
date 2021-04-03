package dragon;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Deque;
import java.util.Objects;

/**
 * A class whose elements make up a collection
 */
public class Dragon implements Comparable<Dragon>, Serializable {
    private int id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDate creationDate;
    private Long age;
    private String description;
    private int weight;
    private DragonType type;
    private Person killer;

    public Dragon(String name, Coordinates coordinates, Long age, String description, int weight, DragonType type, Person killer) {
        creationDate = LocalDate.now();
        this.name = name;
        this.coordinates = coordinates;
        this.age = age;
        this.description = description;
        this.weight = weight;
        this.type = type;
        this.killer = killer;
    }

    public Dragon(int id, String name, Coordinates coordinates, Long age, String description, int weight, DragonType type, Person killer) {
        creationDate = LocalDate.now();
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.age = age;
        this.description = description;
        this.weight = weight;
        this.type = type;
        this.killer = killer;
    }

    public int findMaxId(Deque<Dragon> dragons) {
        int maxId = 0;
        for (Dragon d: dragons) {
            if (d.getId()>maxId) {
                maxId = d.getId();
            }
        }
        maxId++;
        return maxId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Long getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public DragonType getType() {
        return type;
    }

    public Person getKiller() {
        return killer;
    }

    @Override
    public int compareTo(Dragon dragon) {
        return this.getId() - dragon.getId();
    }

    @Override
    public String toString() {
        return "Dragon{" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", coordinates = " + coordinates +
                ", creationDate = " + creationDate +
                ", age = " + age +
                ", description = '" + description + '\'' +
                ", weight = " + weight +
                ", type = " + type +
                ", killer = " + killer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dragon dragon = (Dragon) o;
        return id == dragon.id &&
                weight == dragon.weight &&
                Objects.equals(name, dragon.name) &&
                Objects.equals(coordinates, dragon.coordinates) &&
                Objects.equals(creationDate, dragon.creationDate) &&
                Objects.equals(age, dragon.age) &&
                Objects.equals(description, dragon.description) &&
                type == dragon.type &&
                Objects.equals(killer, dragon.killer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, age, description, weight, type, killer);
    }
}