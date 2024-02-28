import cycling.IllegalNameException;
import cycling.InvalidLengthException;
import cycling.InvalidNameException;
import cycling.Stage;

import java.util.ArrayList;

public class StageTestApp {
    public static void main(String[] args) throws IllegalNameException, InvalidNameException, InvalidLengthException {
        ArrayList<Stage> stageInstances = new ArrayList<>();

        Stage a = Stage.createStage(stageInstances, "a", "description", 5, null, null);
        Stage b = Stage.createStage(stageInstances, "b", "description", 5, null, null);

        System.out.println(Stage.toString(stageInstances));
    }
}
