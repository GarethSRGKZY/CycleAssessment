package cycling;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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

    public LocalTime[] getCheckpointTimes() {
        return this.checkpointTimes;
    }

    public LocalTime getElapsedTime() {
        LocalTime startTime = this.checkpointTimes[0];
        LocalTime endTime = this.checkpointTimes[this.checkpointTimes.length - 1];
        int _hours = (int) startTime.until(endTime, ChronoUnit.HOURS);
        int _minutes = (int) startTime.until(endTime, ChronoUnit.MINUTES);
        int _seconds = (int) startTime.until(endTime, ChronoUnit.SECONDS);
        int _nanoseconds = (int) startTime.until(endTime, ChronoUnit.NANOS);

        int hours = _hours;
        int minutes = _minutes - 60 * _hours;
        int seconds = _seconds - 60 * _minutes;
        int nanoseconds = _nanoseconds - (int) 1e9 * _seconds; 
        return LocalTime.of(hours, minutes, seconds, nanoseconds);
    }

    public String toString(){
        return String.format("Result[stage=%s, rider=%s, checkpointTimes=%s]", this.stage, this.rider, this.checkpointTimes);
    }
}
