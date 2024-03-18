package cycling;

import java.util.ArrayList;

public class Race {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    public static Race findRaceById(ArrayList<Race> raceInstances, int raceId) throws IDNotRecognisedException {
        for (Race race : raceInstances) {
            if (race.getId() == raceId) {
                return race;
            }
        }

        throw new IDNotRecognisedException(String.format("Race id %d not found", raceId));
    }
    
    public static Race findRaceByName(ArrayList<Race> raceInstances, String raceName) throws NameNotRecognisedException {
        for (Race race : raceInstances) {
            if (race.getName().equals(raceName)) {
                return race;
            }
        }

        throw new NameNotRecognisedException(String.format("Race name %s not found", raceName));
    }

    public static void eraseRaces(ArrayList<Race> raceInstances) {
        raceInstances.clear();
        nextId = 0;
    }
    // TODO loadRaces()
    // TODO saveRaces()

    public static int[] toIds(ArrayList<Race> raceInstances) {
        int size = raceInstances.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = raceInstances.get(i).getId();
        }

        return result;
    }

    public static String toString(ArrayList<Race> raceInstances) {
        String[] raceStrings = new String[raceInstances.size()];

        for (int i = 0; i < raceInstances.size(); ++i) {
            raceStrings[i] = raceInstances.get(i).toString();
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

        this.id = nextId++;
        this.stages = new ArrayList<>();

        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Stage> getStages() {
        return this.stages;
    }

    public void addStage(Stage stage) throws IllegalNameException {
        try {
            Stage existingStage = Stage.findStageByName(this.stages, stage.getName());

            if (existingStage instanceof Stage) {
                assert this.stages.contains(existingStage)
                    : "There should be a Stage with the same name in this.stages";
                
                throw new IllegalNameException(String.format("Stage name %s already exists", stage.getName()));
            }
        } catch (NameNotRecognisedException e) {
            // Do nothing - name is unique
        }

        assert !this.stages.contains(stage)
            : "There should not be any existing references to a brand new Stage";

        this.stages.add(stage);

        assert this.stages.contains(stage)
            : "The new Stage should have been appended to this.stages";
    }

    public void removeStage(Stage stage) {
        assert this.stages.contains(stage)
            : "The Stage selected for removal should exist in this.stages";

        this.stages.remove(stage);

        assert !this.stages.contains(stage)
            : "There should not be any references to a removed Stage";
    }



    public String toString() {
        String stages = Stage.toString(this.stages);
        return String.format("Race[id=%d, stages=%s, name=%s, description=%s]", this.id, stages, this.name, this.description);
    }

    public int getId() {
        return this.id;
    }
}