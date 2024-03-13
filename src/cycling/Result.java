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
    public static ArrayList<Result> findResultsByStageId(ArrayList<Result> resultInstances, int stageId) {
        ArrayList<Result> results = new ArrayList<>();

        for (Result result : resultInstances) {
            if (result.getStage().getId() == stageId) {
                results.add(result);
            }
        }

        return results;
    }
    public static ArrayList<Result> findResultsByRiderId(ArrayList<Result> resultInstances, int riderId) {
        ArrayList<Result> results = new ArrayList<>();

        for (Result result : resultInstances) {
            if (result.getRider().getId() == riderId) {
                results.add(result);
            }
        }

        return results;
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
    public Result(Stage stage, Rider rider, LocalTime... checkpoints) throws InvalidCheckpointTimesException, InvalidStageStateException {
        if (checkpoints.length != stage.getCheckpoints().size() + 2) {
            throw new InvalidCheckpointTimesException(String.format("Length of checkpoints %d must be length of checkpoints in stage + 2 %d", checkpoints.length, stage.getCheckpoints().size() + 2));
        }
        assert checkpoints.length >= 2
            : "There should at least be a start time and end time in a result";

        if (stage.getState() != StageState.WAITING_FOR_RESULTS) {
            throw new InvalidStageStateException(String.format("Stage state cannot be %s", stage.getState())); 
        }


        for (int i = 1; i < checkpoints.length; i++) {
            if (checkpoints[i - 1].compareTo(checkpoints[i]) > 1) {
                throw new InvalidCheckpointTimesException(String.format("Checkpoint times %s and %s are not in chronological order", checkpoints[i - 1], checkpoints[i]));
            }
        }

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
