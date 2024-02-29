package cycling;

import java.util.ArrayList;

public class Checkpoint {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    public static int[] getCheckpointIds(ArrayList<Checkpoint> checkpointInstances) {
        int size = checkpointInstances.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = checkpointInstances.get(i).getId();
        }

        return result;
    }

    public static Checkpoint getCheckpointById(ArrayList<Checkpoint> checkpointInstances, int id) throws IDNotRecognisedException {
        for (Checkpoint checkpoint : checkpointInstances) {
            if (checkpoint.getId() == id) {
                return checkpoint;
            }
        }

        throw new IDNotRecognisedException("Checkpoint id %d not found".formatted(id));
    }

    public static void removeCheckpointById(ArrayList<Checkpoint> checkpointInstances, int id) throws IDNotRecognisedException {
        checkpointInstances.remove(getCheckpointById(checkpointInstances, id));
    }

    public static Checkpoint createCheckpoint(ArrayList<Checkpoint> checkpointInstances, double location, CheckpointType type, double averageGradient, double length) {
        Checkpoint checkpoint = new Checkpoint(location, type, averageGradient, length);
        return checkpoint;
    }

    public static Checkpoint createCheckpoint(ArrayList<Checkpoint> checkpointInstances, double location) {
        Checkpoint checkpoint = new Checkpoint(location);
        return checkpoint;
    }

    // TODO loadCheckpoints()
    // TODO saveCheckpoints()

  
  
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
    private Checkpoint(double location, CheckpointType type, double averageGradient, double length) {
        this.id = nextId++;

        this.location = location;
        this.type = type;
        this.averageGradient = averageGradient;
        this.length = length;
    }

    private Checkpoint(double location) {
        this(location, CheckpointType.SPRINT, 0, 0);
    }



    public double getLocation() {
        return this.location;
    }



    public String toString() {
        return "Checkpoint[location=%s, type=%s, averageGradient=%f, length=%f]".formatted(this.location, this.type, this.averageGradient, this.length);
    }

    public int getId() {
        return this.id;
    }
}
