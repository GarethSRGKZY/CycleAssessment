package cycling;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Stage {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    public static int[] getStageIds(ArrayList<Stage> stageInstances) {
        int size = stageInstances.size();

        int[] result = new int[size]; 

        for (int i = 0; i < size; i++) {
            result[i] = stageInstances.get(i).getId();
        }

        return result;
    }

    public static Stage getStageById(ArrayList<Stage> stageInstances, int id) {
        return stageInstances.get(Integer.valueOf(id));
    }

    public static void removeStageById(ArrayList<Stage> stageInstances, int id) {
        stageInstances.remove(getStageById(stageInstances, id));
    }

    public static Stage createStage(ArrayList<Stage> stageInstances, String name, String description, double length, LocalDateTime startTime, StageType type) {
        Stage stage = new Stage(name, description, length, startTime, type);
        stageInstances.add(stage);
        return stage;
    }

    // TODO loadStages()
    // TODO saveStages()



    // Instance Attributes
    private int id = 0;
    private ArrayList<Checkpoint> checkpoints;

    private String name;
    private String description;
    private double length;
    private LocalDateTime startTime;
    private StageType type; 

    // Instance Methods
    private Stage(String name, String description, double length, LocalDateTime startTime, StageType type) {
        this.id = nextId++;
        this.checkpoints = new ArrayList<>();

        this.name = name;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.type = type;
    }

    public double getLength() {
        return this.length;
    }

    public ArrayList<Checkpoint> getCheckpoints() {
        return this.checkpoints;
    }

    public void addCheckpoint(Checkpoint checkpoint) {
        this.checkpoints.add(checkpoint);
    }

    public void removeCheckpoint(Checkpoint checkpoint) {
        this.checkpoints.remove(checkpoint);
    }



    public String toString() {
        String checkpoints = Checkpoint.toString(this.checkpoints);
        return "Stage[id=%d, checkpoints=%s, name=%s, description=%s, length=%s, startTime=%s, type=%s]".formatted(this.id, checkpoints, this.name, this.description, this.length, this.startTime, this.type);
    }

    public int getId() {
        return this.id;
    }

}
