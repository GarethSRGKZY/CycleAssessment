package cycling;

import java.io.Serializable;
import java.util.ArrayList;

public class Checkpoint implements Serializable {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    public static Checkpoint findCheckpointById(ArrayList<Checkpoint> checkpoints, int checkpointId) throws IDNotRecognisedException {
        for (Checkpoint checkpoint : checkpoints) {
            if (checkpoint.getId() == checkpointId) {
                return checkpoint;
            }
        }

        throw new IDNotRecognisedException(String.format("Checkpoint id %d not found", checkpointId));
    }

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

    public static void eraseCheckpoints() {
        nextId = 0;
    }

    public static void loadCheckpoints(ArrayList<Checkpoint> checkpoints) {
        int[] checkpointIds = toIds(checkpoints);

        for (int checkpointId : checkpointIds) {
            if (checkpointId >= nextId) {
                nextId = checkpointId + 1;
            }
        }
    }

    public static int[] toIds(ArrayList<Checkpoint> checkpoints) {
        int size = checkpoints.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = checkpoints.get(i).getId();
        }

        return result;
    }

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
    private int id;
    private double location;
    private double averageGradient;
    private double length;
    private CheckpointType type;

    // Instance Methods
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

    public Checkpoint(double location) throws InvalidLocationException {
        this(location, CheckpointType.SPRINT, 0, 0);
    }



    public double getLocation() {
        return location;
    }

    public CheckpointType getType() {
        return type;
    }



    public String toString() {
        return String.format("Checkpoint[location=%s, type=%s, averageGradient=%f, length=%f]", location, type, averageGradient, length);
    }

    public int getId() {
        return id;
    }
}
