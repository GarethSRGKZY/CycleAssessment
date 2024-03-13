package cycling;

import java.time.LocalTime;
import java.util.ArrayList;

public class Result {
    // Static Attributes

    // Static Methods
    public static Result findResultById(ArrayList<Result> resultInstances, int stageId, int riderId) throws IDNotRecognisedException {
        for (Result result : resultInstances) {
            if (result.getStage().getId() == stageId && result.getRider().getId() == riderId) {
                return result;    
            }
        }

        throw new IDNotRecognisedException(String.format("Stage id %d and Rider id %d not found", stageId, riderId));
    }

    // TODO loadResults()
    // TODO saveResults()

    public static String toString(ArrayList<Result> resultInstances) {
        String[] resultStrings = new String[resultInstances.size()];

        for (int i = 0; i < resultInstances.size(); ++i) {
            resultStrings[i] = resultInstances.get(i).toString();
        }

        String result = "{";

        result += String.join(", ", resultStrings);

        result += "}";

        return result;
    }


    // Instance Attributes
    private Stage stage;
    private Rider rider;
    private LocalTime[] checkpointTimes;

    // Instance Methods
    public Result(Stage stage, Rider rider, LocalTime... checkpoints) {
        this.stage = stage;
        this.rider = rider;
        this.checkpointTimes = checkpoints;
    }

    public Stage getStage() {
        return this.stage;
    }

    public Rider getRider() {
        return this.rider;
    }

    public String toString(){
        return String.format("Result[stage=%s, rider=%s, checkpointTimes=%s]", this.stage, this.rider, this.checkpointTimes);
    }
}
