package usedcars.model;

import java.util.ArrayList;
import java.util.List;

public class Car {
    public enum Status {
        GREAT, NORMAL, BAD
    }

    private final String brand;
    private final String type;
    private final int age;
    private final Status status;
    private final List<KmState> kmStates;

    public Car(String brand, String type, int age, Status status, List<KmState> kmStates) {
        this.brand = brand;
        this.type = type;
        this.age = age;
        this.status = status;
        this.kmStates = kmStates;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public int getAge() {
        return age;
    }

    public Status getStatus() {
        return status;
    }

    public List<KmState> getKmStates() {
        return new ArrayList<>(kmStates);
    }

}
