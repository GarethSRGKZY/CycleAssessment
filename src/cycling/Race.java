package cycling;

import java.util.ArrayList;

public class Race {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    public static int[] getRaceIds(ArrayList<Race> raceInstances) {
        int size = raceInstances.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = raceInstances.get(i).getId();
        }

        return result;
    }

    public static Race getRaceById(ArrayList<Race> raceInstances, int id) throws IDNotRecognisedException {
        for (Race race : raceInstances) {
            if (race.getId() == id) {
                return race;
            }
        }

        throw new IDNotRecognisedException("Race id %d not found".formatted(id));
    }
    
    public static Race getRaceByName(ArrayList<Race> raceInstances, String name) throws NameNotRecognisedException {
        for (Race race : raceInstances) {
            if (race.getName().equals(name)) {
                return race;
            }
        }

        throw new NameNotRecognisedException("Race name %s not found".formatted(name));
    }

    public static void removeRaceById(ArrayList<Race> raceInstances, int id) throws IDNotRecognisedException {
        raceInstances.remove(getRaceById(raceInstances, id));
    }

    public static Race createRace(ArrayList<Race> raceInstances, String name, String description) throws IllegalNameException, InvalidNameException {
        try {
            if (getRaceByName(raceInstances, name) instanceof Race) {
                throw new IllegalNameException("Race name %s already exists".formatted(name));
            }
        } catch (NameNotRecognisedException e) {
            // Do nothing - name is unique
        }

        if (name == null) {
            throw new InvalidNameException("Race name is null");
        }

        if (name.isEmpty()) {
            throw new InvalidNameException("Race name is empty");
        }

        if (name.length() > 30) {
            throw new InvalidNameException("Race name has more than 30 characters (%d)".formatted(name.length()));
        }
        
        if (name.contains(" ")) {
            throw new InvalidNameException("Race name %d contains spaces".formatted(name));
        }

        Race race = new Race(name, description);
        raceInstances.add(race);
        return race;
    }

    // TODO loadRaces()
    // TODO saveRaces()

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
    private Race(String name, String description) { // Constructor
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
        return "Race[id=%d, stages=%s, name=%s, description=%s]".formatted(this.id, stages, this.name, this.description);
    }

    public int getId() {
        return this.id;
    }
}