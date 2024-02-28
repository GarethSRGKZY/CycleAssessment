package cycling;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

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

    public static String toString(ArrayList<Stage> stageInstances) {
        String[] stageStrings = new String[stageInstances.size()];

        for (int i = 0; i < stageInstances.size(); ++i) {
            stageStrings[i] = stageInstances.get(i).toString();
        }

        String result = "{";

        result += String.join(", ", stageStrings);

        result += "}";

        return result;
    }



    // Instance Attributes
    private int id = 0;
    private ArrayList<Integer> checkpoints;

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

    public int[] getCheckpoints() {
        int size = this.checkpoints.size();
        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = this.checkpoints.get(i);
        }

        return result;
    }

    public void addCheckpoint(int id) {
        this.checkpoints.add(id);
    }

    public void removeCheckpoint(int id) {
        this.checkpoints.remove(Integer.valueOf(id));
    }



    public String toString() {
        String checkpoints = Arrays.toString(this.getCheckpoints());
        return "Stage[id=%d, checkpoints=%s, name=%s, description=%s, length=%s, startTime=%s, type=%s]".formatted(this.id, checkpoints, this.name, this.description, this.length, this.startTime, this.type);
    }

    public int getId() {
        return this.id;
    }

}
