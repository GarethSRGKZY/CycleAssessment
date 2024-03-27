package cycling;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Stage is a class representing each Stage belonging to a Race.
 * Each Stage can own multiple Checkpoint objects.
 * 
 * @author 730049785
 * @author 730099588
 * @version 1.0
 * 
 */
public class Stage implements Serializable {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    /**
     * Finds the first Stage in a given list of Stages with a matching stageId.
     * <p>
     * A helper method.
     * 
     * @param stages  ArrayList of Stage objects to be searched through.
     * @param stageId ID of the Stage being queried.
     * @return        A single Stage object with an id field that matches the given stageId.
     * @throws IDNotRecognisedException If the ID does not match to any Stage in the given list.
     */
    public static Stage findStageById(ArrayList<Stage> stages, int stageId) throws IDNotRecognisedException {
        for (Stage stage : stages) {
            if (stage.getId() == stageId) {
                return stage;
            }
        }

        throw new IDNotRecognisedException(String.format("Stage id %d not found", stageId));
    }

    /**
     * Finds the first Stage in a given list of Races with a matching stageId.
     * <p>
     * A helper method.
     * 
     * @param races   ArrayList of Race objects to be searched through.
     * @param stageId ID of the Stage being queried.
     * @return        A single Stage object with an id field that matches the given stageId.
     * @throws IDNotRecognisedException If the ID does not match to any Stage in the given list.
     */
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

    /**
     * Finds the first Stage in a given list of Races that contain a Checkpoint with a matching checkpointId.
     * <p>
     * A helper method.
     * 
     * @param races        ArrayList of Race objects to be searched through.
     * @param checkpointId ID of the Checkpoint being queried.
     * @return             A single Stage object containing a Checkpoint
     *                     with its id field matching the given checkpointId.
     * @throws IDNotRecognisedException If the checkpointId does not exist in any Races in the given list.
     */
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

    /**
     * Finds the first Stage in a given list of Stages with a matching stageName.
     * <p>
     * A helper method.
     * 
     * @param stages    ArrayList of Stage objects to be searched through.
     * @param stageName Name of the Stage being queried.
     * @return          A single Stage object with a name field that matches the given stageName.
     * @throws NameNotRecognisedException If the name does not match to any Stage in the given list.
     */
    public static Stage findStageByName(ArrayList<Stage> stages, String stageName) throws NameNotRecognisedException {
        for (Stage stage : stages) {
            if (stage.getName() == stageName) {
                return stage;
            }
        }

        throw new NameNotRecognisedException(String.format("Stage name %s not found", stageName));
    }

    /**
     * Resets the internal nextId counter of the Stage class.
     */
    public static void eraseStages() {
        nextId = 0;
    }

    /**
     * Reads a given list of Stages for updating the nextId counter to prevent stageId clashes.
     * 
     * @param stages ArrayList of Stage objects that should be registered in the system.
     */
    public static void loadStages(ArrayList<Stage> stages) {
        int[] stageIds = toIds(stages);

        for (int stageId : stageIds) {
            if (stageId >= nextId) {
                nextId = stageId + 1;
            }
        }
    }

    /**
     * Converts a given list of Stages into an array of its IDs.
     * <p>
     * A helper method.
     * 
     * @param stages ArrayList of Stage objects to be converted into IDs.
     * @return       Array of integers representing each Stage's ID in the given list.
     */
    public static int[] toIds(ArrayList<Stage> stages) {
        int size = stages.size();

        int[] result = new int[size]; 

        for (int i = 0; i < size; i++) {
            result[i] = stages.get(i).getId();
        }

        return result;
    }

    /**
     * Converts a given list of Stages into a single string containing each Stage's details.
     * <p>
     * A helper method.
     * 
     * @param stages ArrayList of Stage objects to be included in the string.
     * @return       A single string containing details of all Stages in the given list.
     */
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
    private final int id; // Must be unique - final to prevent unexpected clashes
    private ArrayList<Checkpoint> checkpoints;

    private final String name; // Must be unique - final to prevent unexpected clashes
    private String description;
    private double length;
    private LocalDateTime startTime;
    private StageType type; 
    private StageState state;

    // Instance Methods
    /**
     * Constructs a Stage instance and validates its name and length.
     * 
     * @param name        An identifier name for the Stage.
     * @param description A descriptive text for the Stage.
     * @param length      Stage length in kilometres.
     * @param startTime   The date and time in which the Stage will be raced. It
	 *                    cannot be null.
     * @param type        The type of the Stage. This is used to determine the
	 *                    amount of points given to the winner.
     * @throws InvalidNameException   If the name is null, empty, has more than 30
	 *                                characters, or has white spaces.
     * @throws InvalidLengthException If the length is less than 5km.
     */
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

    /**
     * Getter for the name field of the current Stage.
     * 
     * @return Name of the current Stage.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the length field of the current Stage.
     * 
     * @return Length of the current Stage in kilometres.
     */
    public double getLength() {
        return length;
    }
    
    /**
     * Getter for the startTime field of the current Stage.
     * 
     * @return Start time of the current Stage.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Getter for the type field of the current Stage.
     * 
     * @return Type of the current Stage.
     */
    public StageType getType() {
        return type;
    }

    /**
     * Getter for the state field of the current Stage.
     * 
     * @return State of the current Stage.
     */
    public StageState getState() {
        return state;
    }


    
    /**
     * Getter for the checkpoints field of the current Stage.
     * 
     * @return ArrayList of Checkpoints belonging to the current Stage.
     */
    public ArrayList<Checkpoint> getCheckpoints() {
        return checkpoints;
    }
    
    /**
     * Adds a Checkpoint to the current Stage.
     * Validates against the current Stage's state, type and the new Checkpoint's location against the Stage length.
     * 
     * @param checkpoint Checkpoint object to be added to the current Race.
     * @throws InvalidStageStateException If the Stage is "waiting for results".
     *                                    (If the state field is StageState.WAITING_FOR_RESULTS.)
     * @throws InvalidStageTypeException  Time-trial stages cannot contain any
	 *                                    checkpoint.
     * @throws InvalidLocationException   If the location is out of bounds of the
	 *                                    Stage length.
     */
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
    
    /**
     * Drops the reference to the given Checkpoint from the current Stage.
     * 
     * @param checkpoint Checkpoint object to be removed from the current Stage.
     * @throws InvalidStageStateException If the Stage is "waiting for results".
     *                                    (If the state field is StageState.WAITING_FOR_RESULTS.)
     */
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



    /**
     * Changes the state from PREPARING_STAGE to WAITING_FOR_RESULTS.
     * Validates against concluding an already concluded Stage.
     * 
     * @throws InvalidStageStateException If the Stage is already concluded.
     *                                    (If the state field is StageState.WAITING_FOR_RESULTS.)
     */
    public void concludePreparation() throws InvalidStageStateException {
        if (state == StageState.WAITING_FOR_RESULTS) {
            throw new InvalidStageStateException(String.format("Stage state cannot be %s", state));
        }
    
        state = StageState.WAITING_FOR_RESULTS;
    }
    


    /**
     * Converts the current Stage's details into a String.
     * 
     * @return String containing values for all fields of the Stage.
     */
    public String toString() {
        String checkpointsString = Checkpoint.toString(checkpoints);
        return String.format("Stage[id=%d, checkpoints=%s, name=%s, description=%s, length=%s, startTime=%s, type=%s, state=%s]", id, checkpointsString, name, description, length, startTime, type, state);
    }

    /**
     * Getter for the id field of the current Stage.
     * 
     * @return ID of the current Stage.
     */
    public int getId() {
        return id;
    }

}
