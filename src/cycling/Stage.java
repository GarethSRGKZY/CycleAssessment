package cycling;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Stage {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    public static Stage findStageById(ArrayList<Stage> stageInstances, int stageId) throws IDNotRecognisedException {
        for (Stage stage : stageInstances) {
            if (stage.getId() == stageId) {
                return stage;
            }
        }

        throw new IDNotRecognisedException(String.format("Stage id %d not found", stageId));
    }

    public static Stage findStageByName(ArrayList<Stage> stageInstances, String stageName) throws NameNotRecognisedException {
        for (Stage stage : stageInstances) {
            if (stage.getName() == stageName) {
                return stage;
            }
        }

        throw new NameNotRecognisedException(String.format("Stage name %s not found", stageName));
    }

    // TODO loadStages()
    // TODO saveStages()

    public static int[] toIds(ArrayList<Stage> stageInstances) {
        int size = stageInstances.size();

        int[] result = new int[size]; 

        for (int i = 0; i < size; i++) {
            result[i] = stageInstances.get(i).getId();
        }

        return result;
    }

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
    private ArrayList<Checkpoint> checkpoints;

    private String name;
    private String description;
    private double length;
    private LocalDateTime startTime;
    private StageType type; 
    private StageState state;

    // Instance Methods
    public Stage(String name, String description, double length, LocalDateTime startTime, StageType type) {
        this.id = nextId++;
        this.checkpoints = new ArrayList<>();

        this.name = name;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.type = type;
        this.state = StageState.PREPARING_STAGE;
    }

    public String getName() {
        return this.name;
    }

    public double getLength() {
        return this.length;
    }
    
    public LocalDateTime getStartTime() {
        return this.startTime;
    }


    
    public ArrayList<Checkpoint> getCheckpoints() {
        return this.checkpoints;
    }
    
    public void addCheckpoint(Checkpoint checkpoint) throws InvalidStageStateException, InvalidLocationException, InvalidStageTypeException {
        if (this.state == StageState.WAITING_FOR_RESULTS) {
            throw new InvalidStageStateException(String.format("Stage state cannot be %s", this.state));
        }
        
        if (this.type == StageType.TT) {
            throw new InvalidStageTypeException("Time-trial stages cannot contain any checkpoints");
        }

        if (checkpoint.getLocation() > this.length) {
            throw new InvalidLocationException(String.format("Checkpoint location %f must be less than the Stage length %f", checkpoint.getLocation(), this.length));
        }

        if (checkpoint.getLocation() <= 0) {
            throw new InvalidLocationException(String.format("Checkpoint location %f must be greater than 0", checkpoint.getLocation(), this.length));
        }

        this.checkpoints.add(checkpoint);
    }
    
    public void removeCheckpoint(Checkpoint checkpoint) throws InvalidStageStateException {
        if (this.state == StageState.WAITING_FOR_RESULTS) {
            throw new InvalidStageStateException(String.format("Stage state cannot be %s", this.state));
        }
        
        this.checkpoints.remove(checkpoint);
    }



    public void concludePreparation() {
        this.state = StageState.WAITING_FOR_RESULTS;
    }
    


    public String toString() {
        String checkpoints = Checkpoint.toString(this.checkpoints);
        return String.format("Stage[id=%d, checkpoints=%s, name=%s, description=%s, length=%s, startTime=%s, type=%s, state=%s]", this.id, checkpoints, this.name, this.description, this.length, this.startTime, this.type, this.state);
    }

    public int getId() {
        return this.id;
    }

}
