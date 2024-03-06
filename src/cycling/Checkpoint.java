package cycling;

import java.util.ArrayList;

public class Checkpoint {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    public static Checkpoint findCheckpointById(ArrayList<Checkpoint> checkpointInstances, int checkpointId) throws IDNotRecognisedException {
        for (Checkpoint checkpoint : checkpointInstances) {
            if (checkpoint.getId() == checkpointId) {
                return checkpoint;
            }
        }

        throw new IDNotRecognisedException(String.format("Checkpoint id %d not found", checkpointId));
    }

    // TODO loadCheckpoints()
    // TODO saveCheckpoints()

    public static int[] toIds(ArrayList<Checkpoint> checkpointInstances) {
        int size = checkpointInstances.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = checkpointInstances.get(i).getId();
        }

        return result;
    }

    public static String toString(ArrayList<Checkpoint> checkpointInstances) {
        String[] checkpointStrings = new String[checkpointInstances.size()];

        for (int i = 0; i < checkpointInstances.size(); ++i) {
            checkpointStrings[i] = checkpointInstances.get(i).toString();
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

        this.id = nextId++;

        this.location = location;
        this.type = type;
        this.averageGradient = averageGradient;
        this.length = length;
    }

    public Checkpoint(double location) throws InvalidLocationException {
        this(location, CheckpointType.SPRINT, 0, 0);
    }



    public double getLocation() {
        return this.location;
    }



    public String toString() {
        return String.format("Checkpoint[location=%s, type=%s, averageGradient=%f, length=%f]", this.location, this.type, this.averageGradient, this.length);
    }

    public int getId() {
        return this.id;
    }
}
