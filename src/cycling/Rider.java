package cycling;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Rider is a class representing each Rider belonging to a Team.
 * 
 * @author 730049785
 * @version 1.0
 * 
 */
public class Rider implements Serializable {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    /**
     * Finds the first Rider in a given list of Riders with a matching riderId.
     * <p>
     * A helper method.
     * 
     * @param riders  ArrayList of Rider objects to be searched through.
     * @param riderId ID of the Rider being queried.
     * @return        A single Rider object with an id field that matches the given riderId.
     * @throws IDNotRecognisedException If the ID does not match to any Riders in the given list.
     */
    public static Rider findRiderById(ArrayList<Rider> riders, int riderId) throws IDNotRecognisedException {
        for (Rider rider : riders) {
            if (rider.getId() == riderId) {
                return rider;
            }
        }

        throw new IDNotRecognisedException(String.format("Rider id %d not found", riderId));
    }

    /**
     * Finds the first Rider in a given list of Teams with a matching riderId.
     * <p>
     * A helper method.
     * 
     * @param teams   ArrayList of Team objects to be searched through.
     * @param riderId ID of the Rider being queried.
     * @return        A single Rider object with an id field that matches the given riderId.
     * @throws IDNotRecognisedException If the ID does not match to any Stage in the given list.
     */
    public static Rider findRiderByIdFromTeams(ArrayList<Team> teams, int riderId) throws IDNotRecognisedException {
        for (Team team : teams) {
            try {
                return Rider.findRiderById(team.getRiders(), riderId);
            } catch (IDNotRecognisedException e) {
                continue;
            }
        }

        throw new IDNotRecognisedException(String.format("Rider id %d not found", riderId));
    }

    /**
     * Resets the internal nextId counter of the Rider class.
     */
    public static void eraseRiders() {
        nextId = 0;
    }

    /**
     * Reads a given list of Riders for updating the nextId counter to prevent riderId clashes.
     * 
     * @param riders ArrayList of Rider objects that should be registered in the system.
     */
    public static void loadRiders(ArrayList<Rider> riders) {
        int[] riderIds = toIds(riders);

        for (int riderId : riderIds) {
            if (riderId >= nextId) {
                nextId = riderId + 1;
            }
        }
    }

    /**
     * Converts a given list of Riders into an array of its IDs.
     * <p>
     * A helper method.
     * 
     * @param riders ArrayList of Rider objects to be converted into IDs.
     * @return       Array of integers representing each Rider's ID in the given list.
     */
    public static int[] toIds(ArrayList<Rider> riders) {
        int size = riders.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = riders.get(i).getId();
        }

        return result;
    }

    /**
     * Converts a given list of Riders into a single string containing each Rider's details.
     * <p>
     * A helper method.
     * 
     * @param riders ArrayList of Rider objects to be included in the string.
     * @return       A single string containing details of all Riders in the given list.
     */
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
    private final int id; // Must be unique - final to prevent unexpected clashes

    private String name; // Does not have to be unique
    private int yearOfBirth;

    // Instance Methods
    /**
     * Constructs a Rider instance and validates its name and year of birth.
     * 
     * @param name        The name of the Rider.
     * @param yearOfBirth The year of birth of the Rider.
     * @throws IllegalArgumentException If the name of the Rider is null or empty,
	 *                                  or the year of birth is less than 1900.
     */
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

    /**
     * Converts the current Rider's details into a String.
     * 
     * @return String containing values for all fields of the Rider.
     */
    public String toString() {
        return String.format("Rider[id=%d, name=%s, yearOfBirth=%d]", id, name, yearOfBirth); 
    }

    /**
     * Getter for the id field of the current Rider.
     * 
     * @return ID of the current Rider.
     */
    public int getId() {
        return id;
    }
}
