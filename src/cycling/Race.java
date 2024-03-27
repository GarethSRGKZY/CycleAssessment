package cycling;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Race is a class representing each Race belonging to the CyclingPortal.
 * Each Race can own multiple Stage objects.
 * 
 * @author 730049785
 * @version 1.0
 * 
 */
public class Race implements Serializable {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    /**
     * Finds the first Race in a given list of Races with a matching raceId.
     * <p>
     * A helper method.
     * 
     * @param races  ArrayList of Race objects to be searched through.
     * @param raceId ID of the Race being queried.
     * @return       A single Race object with an id field that matches the given raceId.
     * @throws IDNotRecognisedException If the ID does not match to any Race in the given list.
     */
    public static Race findRaceById(ArrayList<Race> races, int raceId) throws IDNotRecognisedException {
        for (Race race : races) {
            if (race.getId() == raceId) {
                return race;
            }
        }

        throw new IDNotRecognisedException(String.format("Race id %d not found", raceId));
    }
    
    /**
     * Finds the first Race in a given list of Races with a matching raceName.
     * <p>
     * A helper method.
     * 
     * @param races    ArrayList of Race objects to be searched through.
     * @param raceName Name of the Race being queried.
     * @return         A single Race object with a name field that matches the given raceName.
     * @throws NameNotRecognisedException If the name does not match to any Race in the given list.
     */
    public static Race findRaceByName(ArrayList<Race> races, String raceName) throws NameNotRecognisedException {
        for (Race race : races) {
            if (race.getName().equals(raceName)) {
                return race;
            }
        }

        throw new NameNotRecognisedException(String.format("Race name %s not found", raceName));
    }

    /**
     * Finds the first Race in a given list of Races that contain a Stage with a matching stageId.
     * <p>
     * A helper method.
     * 
     * @param races   ArrayList of Race objects to be searched through.
     * @param stageId ID of the Stage being queried.
     * @return        A single Race object containing a Stage
     *                with its id field matching the given stageId.
     * @throws IDNotRecognisedException If the stageId does not exist in any Races in the given list.
     */
    public static Race findRaceContainsStageId(ArrayList<Race> races, int stageId) throws IDNotRecognisedException{
        for (Race race : races) {
            try {
                Stage stage = Stage.findStageById(race.getStages(), stageId);
                if (stage instanceof Stage) {
                    return race;
                }
            } catch (IDNotRecognisedException e) {
                continue;
            }
        }

        throw new IDNotRecognisedException(String.format("Stage id %d not found", stageId));
    }

    /**
     * Drops all stored references of Race instances from the given list of Races
     * and resets the internal nextId counter of the Race class.
     * 
     * @param races ArrayList of Results that should be cleared.
     */
    public static void eraseRaces(ArrayList<Race> races) {
        races.clear();
        nextId = 0;
    }

    /**
     * Reads a given list of Races for updating the nextId counter to prevent raceId clashes.
     * 
     * @param races ArrayList of Race objects that should be registered in the system.
     */
    public static void loadRaces(ArrayList<Race> races) {
        int[] raceIds = toIds(races);

        for (int raceId : raceIds) {
            if (raceId >= nextId) {
                nextId = raceId + 1;
            }
        }
    }

    /**
     * Converts a given list of Races into an array of its IDs.
     * <p>
     * A helper method.
     * 
     * @param races ArrayList of Race objects to be converted into IDs.
     * @return      Array of integers representing each Race's ID in the given list.
     */
    public static int[] toIds(ArrayList<Race> races) {
        int size = races.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = races.get(i).getId();
        }

        return result;
    }

    /**
     * Converts a given list of Races into a single string containing each Race's details.
     * <p>
     * A helper method.
     * 
     * @param races ArrayList of Race objects to be included in the string.
     * @return      A single string containing details of all Races in the given list.
     */
    public static String toString(ArrayList<Race> races) {
        String[] raceStrings = new String[races.size()];

        for (int i = 0; i < races.size(); ++i) {
            raceStrings[i] = races.get(i).toString();
        }

        String result = "{";

        result += String.join(", ", raceStrings);

        result += "}";

        return result;
    }



    // Instance Attributes
    private int id;
    private ArrayList<Stage> stages;

    private String name;
    private String description;

    // Instance Methods
    /**
     * Constructs a Race instance and validates its name.
     * 
     * @param name        Race's name.
     * @param description Race's description.
     * @throws InvalidNameException If the name is null, empty, has more than 30
	 *                              characters, or has white spaces.
     */
    public Race(String name, String description) throws InvalidNameException { // Constructor
        if (name == null) {
            throw new InvalidNameException("Race name is null");
        }

        if (name.isEmpty()) {
            throw new InvalidNameException("Race name is empty");
        }

        if (name.length() > 30) {
            throw new InvalidNameException(String.format("Race name has more than 30 characters (%d)", name.length()));
        }
        
        if (name.contains(" ")) {
            throw new InvalidNameException(String.format("Race name %s contains spaces", name));
        }

        id = nextId++;
        stages = new ArrayList<>();

        this.name = name;
        this.description = description;
    }

    /**
     * Getter for the name field of the current Race.
     * 
     * @return Name of the current Race.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the stages field of the current Race.
     * 
     * @return ArrayList of Stages belonging to the current Race.
     */
    public ArrayList<Stage> getStages() {
        return stages;
    }

    /**
     * Adds a Stage to the current Race.
     * Validates against name clashes.
     * 
     * @param stage Stage object to be added to the current Race.
     * @throws IllegalNameException If the new Stage's name already exists in the current Race.
     */
    public void addStage(Stage stage) throws IllegalNameException {
        try {
            Stage existingStage = Stage.findStageByName(stages, stage.getName());

            if (existingStage instanceof Stage) {
                assert stages.contains(existingStage)
                    : "There should be a Stage with the same name in stages";
                
                throw new IllegalNameException(String.format("Stage name %s already exists", stage.getName()));
            }
        } catch (NameNotRecognisedException e) {
            // Do nothing - name is unique
        }

        assert !stages.contains(stage)
            : "There should not be any existing references to a brand new Stage";

        stages.add(stage);

        assert stages.contains(stage)
            : "The new Stage should have been appended to stages";
    }

    /**
     * Drops the reference to the given Stage from the current Race.
     * 
     * @param stage Stage object to be removed from the current Race.
     */
    public void removeStage(Stage stage) {
        assert stages.contains(stage)
            : "The Stage selected for removal should exist in stages";

        stages.remove(stage);

        assert !stages.contains(stage)
            : "There should not be any references to a removed Stage";
    }

    /**
     * Converts the current Race's details into a String.
     * 
     * @return String containing values for all fields of the Race.
     */
    public String toString() {
        String stagesString = Stage.toString(stages);
        return String.format("Race[id=%d, stages=%s, name=%s, description=%s]", id, stagesString, name, description);
    }

    /**
     * Getter for the id field of the current Race.
     * 
     * @return ID of the current Race.
     */
    public int getId() {
        return id;
    }
}