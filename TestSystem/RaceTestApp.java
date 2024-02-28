import java.util.ArrayList;

import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.Race;
import cycling.Stage;

public class RaceTestApp {
    public static void main(String[] args) throws IDNotRecognisedException, IllegalNameException {
        ArrayList<Race> raceInstances = new ArrayList<>();
        ArrayList<Stage> stageInstances = new ArrayList<>();

        Race a = Race.createRace(raceInstances, "name", "description");

        Stage x = Stage.createStage(stageInstances, "a", "description", 0, null, null);
        Stage y = Stage.createStage(stageInstances, "b", "description", 0, null, null);

        a.addStage(x);
        a.addStage(y);

        System.out.println(a);

        a.removeStage(x);

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
