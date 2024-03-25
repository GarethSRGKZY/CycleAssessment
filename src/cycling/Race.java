package cycling;

import java.io.Serializable;
import java.util.ArrayList;

public class Race implements Serializable {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    public static Race findRaceById(ArrayList<Race> races, int raceId) throws IDNotRecognisedException {
        for (Race race : races) {
            if (race.getId() == raceId) {
                return race;
            }
        }

        throw new IDNotRecognisedException(String.format("Race id %d not found", raceId));
    }
    
    public static Race findRaceByName(ArrayList<Race> races, String raceName) throws NameNotRecognisedException {
        for (Race race : races) {
            if (race.getName().equals(raceName)) {
                return race;
            }
        }

        throw new NameNotRecognisedException(String.format("Race name %s not found", raceName));
    }

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

    public static void eraseRaces(ArrayList<Race> races) {
        races.clear();
        nextId = 0;
    }

    public static void loadRaces(ArrayList<Race> races) {
        int[] raceIds = toIds(races);

        for (int raceId : raceIds) {
            if (raceId >= nextId) {
                nextId = raceId + 1;
            }
        }
    }

    public static int[] toIds(ArrayList<Race> races) {
        int size = races.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = races.get(i).getId();
        }

        return result;
    }

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

    public String getName() {
        return name;
    }

    public ArrayList<Stage> getStages() {
        return stages;
    }

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

    public void removeStage(Stage stage) {
        assert stages.contains(stage)
            : "The Stage selected for removal should exist in stages";

        stages.remove(stage);

        assert !stages.contains(stage)
            : "There should not be any references to a removed Stage";
    }



    public String toString() {
        String stagesString = Stage.toString(stages);
        return String.format("Race[id=%d, stages=%s, name=%s, description=%s]", id, stagesString, name, description);
    }

    public int getId() {
        return id;
    }
}