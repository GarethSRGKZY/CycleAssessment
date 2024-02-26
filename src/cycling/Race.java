package cycling;

import java.util.ArrayList;
import java.util.Arrays;

public class Race {
    // Static Attributes
    private static int nextId = 0;
    private static ArrayList<Race> raceInstances = new ArrayList<>();

    // Static Methods
    public static int[] getRaceIds() {
        int size = raceInstances.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = raceInstances.get(i).getId();
        }

        return result;
    }

    public static Race getRaceById(int id) {
        return raceInstances.get(Integer.valueOf(id));
    }

    public static void removeRaceById(int id) {
        raceInstances.remove(getRaceById(id));
    }
    // TODO: loadRaces()
    // TODO: saveRaces()



    // Instance Attributes
    private int id;
    private ArrayList<Integer> stages;

    private String name;
    private String description;

    // Instance Methods
    public Race(String name, String description) { // Constructor
        this.id = nextId++;
        this.stages = new ArrayList<>();
        raceInstances.add(this);

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