package cycling;

import java.io.Serializable;
import java.util.ArrayList;

public class Rider implements Serializable {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    public static Rider findRiderById(ArrayList<Rider> riders, int riderId) throws IDNotRecognisedException {
        for (Rider rider : riders) {
            if (rider.getId() == riderId) {
                return rider;
            }
        }

        throw new IDNotRecognisedException(String.format("Rider id %d not found", riderId));
    }

    public static void eraseRiders() {
        nextId = 0;
    }

    public static void loadRiders(ArrayList<Rider> riders) {
        int[] riderIds = toIds(riders);

        for (int riderId : riderIds) {
            if (riderId >= nextId) {
                nextId = riderId + 1;
            }
        }
    }

    public static int[] toIds(ArrayList<Rider> riders) {
        int size = riders.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = riders.get(i).getId();
        }

        return result;
    }

    public static String toString(ArrayList<Rider> riders) {
        String[] riderStrings = new String[riders.size()];

        for (int i = 0; i < riders.size(); ++i) {
            riderStrings[i] = riders.get(i).toString();
        }

        String result = "{";

        result += String.join(", ", riderStrings);

        result += "}";

        return result;
    }

    // Instance Attributes
    private int id;

    private String name;
    private int yearOfBirth;

    // Instance Methods
    public Rider(String name, int yearOfBirth) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Rider name is null");
        }

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Rider name is empty");
        }
        if (yearOfBirth < 1900) {
            throw new IllegalArgumentException(String.format("Rider yearOfBirth %d is less than 1900", yearOfBirth));
        }

        id = nextId++;

        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public String toString() {
        return String.format("Rider[id=%d, name=%s, yearOfBirth=%d]", id, name, yearOfBirth); 
    }

    public int getId() {
        return id;
    }
}
