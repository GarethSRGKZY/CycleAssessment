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

    public void addStage(Stage stage) {
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