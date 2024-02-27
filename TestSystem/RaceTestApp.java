import java.util.ArrayList;

import cycling.Race;

public class RaceTestApp {
    public static void main(String[] args) {
        ArrayList<Race> raceInstances = new ArrayList<>();

        Race a = Race.createRace(raceInstances, "name", "description");

        a.addStage(123);
        a.addStage(456);

        System.out.println(a);

        a.removeStage(123);

        System.out.println(a);



        Race b = Race.createRace(raceInstances, "b", "b");

        System.out.println(b);



        for (int id : Race.getRaceIds(raceInstances)) {
            System.out.println(id);
        }

        System.out.println(raceInstances);

        Race a_test = Race.getRaceById(raceInstances, 0);

        System.out.println(a == a_test);



        // This should remove a
        Race.removeRaceById(raceInstances, 0);

        for (int id : Race.getRaceIds(raceInstances)) {
            System.out.println(id);
        }
    }
}
