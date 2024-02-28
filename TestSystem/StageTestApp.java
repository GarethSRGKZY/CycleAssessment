import cycling.IllegalNameException;
import cycling.Stage;

import java.util.ArrayList;

public class StageTestApp {
    public static void main(String[] args) throws IllegalNameException {
        ArrayList<Stage> stageInstances = new ArrayList<>();

        Stage a = Stage.createStage(stageInstances, "a", "description", 0, null, null);
        Stage b = Stage.createStage(stageInstances, "b", "description", 0, null, null);

        System.out.println(Stage.toString(stageInstances));
    }
}
