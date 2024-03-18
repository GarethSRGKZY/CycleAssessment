package cycling;

import java.util.ArrayList;

public class Rider {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    public static Rider findRiderById(ArrayList<Rider> riderInstances, int riderId) throws IDNotRecognisedException {
        for (Rider rider : riderInstances) {
            if (rider.getId() == riderId) {
                return rider;
            }
        }

        throw new IDNotRecognisedException(String.format("Rider id %d not found", riderId));
    }

    public static void eraseRiders() {
        nextId = 0;
    }
    // TODO loadRiders()
    // TODO saveRiders()

    public static int[] toIds(ArrayList<Rider> riderInstances) {
        int size = riderInstances.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = riderInstances.get(i).getId();
        }

        return result;
    }

    public static String toString(ArrayList<Rider> riderInstances) {
        String[] riderStrings = new String[riderInstances.size()];

        for (int i = 0; i < riderInstances.size(); ++i) {
            riderStrings[i] = riderInstances.get(i).toString();
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

        this.id = nextId++;

        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public String toString() {
        return String.format("Rider[id=%d, name=%s, yearOfBirth=%d]", this.id, this.name, this.yearOfBirth); 
    }

    public int getId() {
        return this.id;
    }
}
