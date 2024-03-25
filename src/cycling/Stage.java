package cycling;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Stage implements Serializable {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    public static Stage findStageById(ArrayList<Stage> stages, int stageId) throws IDNotRecognisedException {
        for (Stage stage : stages) {
            if (stage.getId() == stageId) {
                return stage;
            }
        }

        throw new IDNotRecognisedException(String.format("Stage id %d not found", stageId));
    }

    public static Stage findStageByIdFromRaces(ArrayList<Race> races, int stageId) throws IDNotRecognisedException {
        for (Race race : races) {
            try {
                return findStageById(race.getStages(), stageId);
            } catch (IDNotRecognisedException e) {
                continue;
            }
        }

        throw new IDNotRecognisedException(String.format("Stage id %d not found", stageId));
    }

    public static Stage findStageContainsCheckpoint(ArrayList<Race> races, int checkpointId) throws IDNotRecognisedException {
        for (Race race : races) {
            for (Stage stage : race.getStages()) {
                try {
                    Checkpoint checkpoint = Checkpoint.findCheckpointById(stage.getCheckpoints(), checkpointId);
                    if (checkpoint instanceof Checkpoint) {
                        return stage;
                    }
                } catch (IDNotRecognisedException e) {
                    continue;
                }
            }
        }

        throw new IDNotRecognisedException(String.format("Checkpoint id %d not found", checkpointId));
    }

    public static Stage findStageByName(ArrayList<Stage> stages, String stageName) throws NameNotRecognisedException {
        for (Stage stage : stages) {
            if (stage.getName() == stageName) {
                return stage;
            }
        }

        throw new NameNotRecognisedException(String.format("Stage name %s not found", stageName));
    }

    public static void eraseStages() {
        nextId = 0;
    }

    public static void loadStages(ArrayList<Stage> stages) {
        int[] stageIds = toIds(stages);

        for (int stageId : stageIds) {
            if (stageId >= nextId) {
                nextId = stageId + 1;
            }
        }
    }

    public static int[] toIds(ArrayList<Stage> stages) {
        int size = stages.size();

        int[] result = new int[size]; 

        for (int i = 0; i < size; i++) {
            result[i] = stages.get(i).getId();
        }

        return result;
    }

    public static String toString(ArrayList<Stage> stages) {
        String[] stageStrings = new String[stages.size()];

        for (int i = 0; i < stages.size(); ++i) {
            stageStrings[i] = stages.get(i).toString();
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
    public Stage(String name, String description, double length, LocalDateTime startTime, StageType type) throws InvalidNameException, InvalidLengthException {
        if (name == null) {
            throw new InvalidNameException("Stage name is null");
        }

        if (name.isEmpty()) {
            throw new InvalidNameException("Stage name is empty");
        }

        if (name.length() > 30) {
            throw new InvalidNameException(String.format("Stage name has more than 30 characters (%d)", name.length()));
        }
        
        if (name.contains(" ")) {
            throw new InvalidNameException(String.format("Stage name %s contains spaces", name));
        }

        if (length < 5) {
            throw new InvalidLengthException(String.format("Stage length %f is less than 5", length));
        }

        id = nextId++;
        checkpoints = new ArrayList<>();

        this.name = name;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.type = type;
        state = StageState.PREPARING_STAGE;
    }

    public String getName() {
        return name;
    }

    public double getLength() {
        return length;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public StageType getType() {
        return type;
    }

    public StageState getState() {
        return state;
    }


    
    public ArrayList<Checkpoint> getCheckpoints() {
        return checkpoints;
    }
    
    public void addCheckpoint(Checkpoint checkpoint) throws InvalidStageStateException, InvalidStageTypeException, InvalidLocationException {
        if (state == StageState.WAITING_FOR_RESULTS) {
            throw new InvalidStageStateException(String.format("Stage state cannot be %s", state));
        }
        
        if (type == StageType.TT) {
            throw new InvalidStageTypeException("Time-trial stages cannot contain any checkpoints");
        }

        if (checkpoint.getLocation() > length) {
            throw new InvalidLocationException(String.format("Checkpoint location %f must be less than the Stage length %f", checkpoint.getLocation(), length));
        }

        assert !checkpoints.contains(checkpoint)
            : "There should not be any existing references to a brand new Checkpoint";

        checkpoints.add(checkpoint);

        assert checkpoints.contains(checkpoint)
            : "The new Checkpoint should have been appended to checkpoints";
    }
    
    public void removeCheckpoint(Checkpoint checkpoint) throws InvalidStageStateException {
        if (state == StageState.WAITING_FOR_RESULTS) {
            throw new InvalidStageStateException(String.format("Stage state cannot be %s", state));
        }
        
        assert checkpoints.contains(checkpoint)
            : "The Checkpoint selected for removal should exist in checkpoints";

        checkpoints.remove(checkpoint);

        assert !checkpoints.contains(checkpoint)
            : "There should not be any references to a removed Checkpoint";
    }



    public void concludePreparation() throws InvalidStageStateException {
        if (state == StageState.WAITING_FOR_RESULTS) {
            throw new InvalidStageStateException(String.format("Stage state cannot be %s", state));
        }
    
        state = StageState.WAITING_FOR_RESULTS;
    }
    


    public String toString() {
        String checkpointsString = Checkpoint.toString(checkpoints);
        return String.format("Stage[id=%d, checkpoints=%s, name=%s, description=%s, length=%s, startTime=%s, type=%s, state=%s]", id, checkpointsString, name, description, length, startTime, type, state);
    }

    public int getId() {
        return id;
    }

}
