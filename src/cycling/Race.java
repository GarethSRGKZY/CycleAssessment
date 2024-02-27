package cycling;

import java.util.ArrayList;
import java.util.Arrays;

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

    public static Race getRaceById(ArrayList<Race> raceInstances, int id) {
        return raceInstances.get(Integer.valueOf(id));
    }

    public static void removeRaceById(ArrayList<Race> raceInstances, int id) {
        raceInstances.remove(getRaceById(raceInstances, id));
    }

    public static Race createRace(ArrayList<Race> raceInstances, String name, String description) {
        Race race = new Race(name, description);
        raceInstances.add(race);
        return race;
    }

    // TODO: loadRaces()
    // TODO: saveRaces()



    // Instance Attributes
    private int id;
    private ArrayList<Integer> stages;

    private String name;
    private String description;

    // Instance Methods
    private Race(String name, String description) { // Constructor
        this.id = nextId++;
        this.stages = new ArrayList<>();

        this.name = name;
        this.description = description;
    }

    public int[] getStages() {
        int size = this.stages.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = this.stages.get(i);
        }

        return result;
    }

    public void addStage(int id) {
        this.stages.add(id);
    }

    public void removeStage(int id) {
        this.stages.remove(Integer.valueOf(id));
    }



    public String toString() {
        String stages = Arrays.toString(this.getStages());
        return "Race[id=%d, stages=%s, name=%s, description=%s]".formatted(this.id, stages, this.name, this.description);
    }

    public int getId() {
        return this.id;
    }
}