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
    public Race(String name, String description) { // Constructor
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

    public void addStage(Stage stage) throws IllegalNameException, InvalidNameException, InvalidLengthException {
        String name = stage.getName();
        double length = stage.getLength();

        try {
            if (Stage.findStageByName(this.stages, name) instanceof Stage) {
                throw new IllegalNameException(String.format("Stage name %s already exists", name));
            }
        } catch (NameNotRecognisedException e) {
            // Do nothing - name is unique
        }

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

        this.stages.add(stage);
    }

    public void removeStage(Stage stage) {
        this.stages.remove(stage);
    }



    public String toString() {
        String stages = Stage.toString(this.stages);
        return String.format("Race[id=%d, stages=%s, name=%s, description=%s]", this.id, stages, this.name, this.description);
    }

    public int getId() {
        return this.id;
    }
}