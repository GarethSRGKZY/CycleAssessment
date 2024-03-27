package cycling;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Checkpoint is a class representing each Checkpoint belonging to a Stage.
 * 
 * @author 730049785
 * @version 1.0
 * 
 */
public class Checkpoint implements Serializable {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    /**
     * Finds the first Checkpoint in a given list of Checkpoints with a matching checkpointId.
     * <p>
     * A helper method.
     * 
     * @param checkpoints  ArrayList of Checkpoint objects to be searched through.
     * @param checkpointId ID of the Checkpoint being queried.
     * @return             A single Checkpoint object with an id field that matches the given checkpointId.
     * @throws IDNotRecognisedException If the ID does not match to any Checkpoint in the given list.
     */
    public static Checkpoint findCheckpointById(ArrayList<Checkpoint> checkpoints, int checkpointId) throws IDNotRecognisedException {
        for (Checkpoint checkpoint : checkpoints) {
            if (checkpoint.getId() == checkpointId) {
                return checkpoint;
            }
        }

        throw new IDNotRecognisedException(String.format("Checkpoint id %d not found", checkpointId));
    }

    /**
     * Finds the first Checkpoint in a given list of Races with a matching checkpointId.
     * <p>
     * A helper method.
     * 
     * @param races        ArrayList of Race objects to be searched through.
     * @param checkpointId ID of the Checkpoint being queried.
     * @return             A single Checkpoint object with an id field that matches the given checkpointId.
     * @throws IDNotRecognisedException If the ID does not match to any Checkpoint in the given list.
     */
    public static Checkpoint findCheckpointByIdFromRaces(ArrayList<Race> races, int checkpointId) throws IDNotRecognisedException {
        for (Race race : races) {
            for (Stage stage : race.getStages()) {
                try {
                    return findCheckpointById(stage.getCheckpoints(), checkpointId);
                } catch (IDNotRecognisedException e) {
                    continue;
                }
            }
        }

        throw new IDNotRecognisedException(String.format("Checkpoint id %d not found", checkpointId));
    }

    /**
     * Resets the internal nextId counter of the Checkpoint class.
     */
    public static void eraseCheckpoints() {
        nextId = 0;
    }

    /**
     * Reads a given list of Checkpoints for updating the nextId counter to prevent checkpointId clashes.
     * 
     * @param checkpoints ArrayList of Checkpoint objects that should be registered in the system.
     */
    public static void loadCheckpoints(ArrayList<Checkpoint> checkpoints) {
        int[] checkpointIds = toIds(checkpoints);

        for (int checkpointId : checkpointIds) {
            if (checkpointId >= nextId) {
                nextId = checkpointId + 1;
            }
        }
    }

    /**
     * Converts a given list of Checkpoints into an array of its IDs.
     * <p>
     * A helper method.
     * 
     * @param checkpoints ArrayList of Checkpoint objects to be converted into IDs.
     * @return            Array of integers representing each Checkpoint's ID in the given list.
     */
    public static int[] toIds(ArrayList<Checkpoint> checkpoints) {
        int size = checkpoints.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = checkpoints.get(i).getId();
        }

        return result;
    }

    /**
     * Converts a given list of Checkpoints into a single string containing each Checkpoint's details.
     * <p>
     * A helper method.
     * 
     * @param checkpoints ArrayList of Checkpoint objects to be included in the string.
     * @return            A single string containing details of all Checkpoints in the given list.
     */
    public static String toString(ArrayList<Checkpoint> checkpoints) {
        String[] checkpointStrings = new String[checkpoints.size()];

        for (int i = 0; i < checkpoints.size(); ++i) {
            checkpointStrings[i] = checkpoints.get(i).toString();
        }

        String result = "{";

        result += String.join(", ", checkpointStrings);

        result += "}";

        return result;
    }

    // Instance Attributes
    private final int id;
    private double location;
    private double averageGradient;
    private double length;
    private CheckpointType type;

    // Instance Methods
    /**
     * Constructs a Checkpoint instance of type CategorizedClimb and validates its location.
     * 
     * @param location        The kilometre location where the climb or sprint finishes within the stage.
     * @param type            The category of the climb - {@link CheckpointType#C4},
	 *                        {@link CheckpointType#C3}, {@link CheckpointType#C2},
	 *                        {@link CheckpointType#C1}, or {@link CheckpointType#HC}.
     * @param averageGradient The average gradient for the climb.
     * @param length          The length of the climb in kilometres.
     * @throws InvalidLocationException If the location is out of bounds of the
	 *                                  stage length.
     */
    public Checkpoint(double location, CheckpointType type, double averageGradient, double length) throws InvalidLocationException {
        if (location <= 0) {
            throw new InvalidLocationException(String.format("Checkpoint location %f must be greater than 0", location));
        }

        id = nextId++;

        this.location = location;
        this.type = type;
        this.averageGradient = averageGradient;
        this.length = length;
    }

    /**
     * Constructs a Checkpoint instance of type IntermediateSprint and validates its location.
     * 
     * @param location The kilometre location where the climb or sprint finishes within the stage.
     * @throws InvalidLocationException If the location is out of bounds of the
	 *                                  stage length.
     */
    public Checkpoint(double location) throws InvalidLocationException {
        this(location, CheckpointType.SPRINT, 0, 0);
    }



    /**
     * Getter for the location field of the current Checkpoint.
     * 
     * @return Location of the current Checkpoint.
     */
    public double getLocation() {
        return location;
    }

    /**
     * Getter for the type field of the current Checkpoint.
     * 
     * @return Type of the current Checkpoint.
     */
    public CheckpointType getType() {
        return type;
    }



    /**
     * Converts the current Checkpoint's details into a String.
     * 
     * @return String containing values for all fields of the Checkpoint.
     */
    public String toString() {
        return String.format("Checkpoint[location=%s, type=%s, averageGradient=%f, length=%f]", location, type, averageGradient, length);
    }

    /**
     * Getter for the id field of the current Checkpoint.
     * 
     * @return ID of the current Checkpoint.
     */
    public int getId() {
        return id;
    }
}
