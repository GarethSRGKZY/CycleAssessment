package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Result implements Serializable {
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

    public static void eraseResults(ArrayList<Result> resultInstances) {
        resultInstances.clear();
    }

    public static LocalTime timeDelta(LocalTime time1, LocalTime time2) {
        int _hours = (int) time1.until(time2, ChronoUnit.HOURS);
        int _minutes = (int) time1.until(time2, ChronoUnit.MINUTES);
        int _seconds = (int) time1.until(time2, ChronoUnit.SECONDS);
        int _nanoseconds = (int) time1.until(time2, ChronoUnit.NANOS);

        int hours = _hours;
        int minutes = _minutes - 60 * _hours;
        int seconds = _seconds - 60 * _minutes;
        int nanoseconds = _nanoseconds - (int) 1e9 * _seconds; 
        return LocalTime.of(hours, minutes, seconds, nanoseconds);
    }

    public static LocalTime timeAdd(LocalTime time1, LocalTime time2) {
        time1.plusHours(time2.getHour());
        time1.plusMinutes(time2.getMinute());
        time1.plusSeconds(time2.getSecond());
        time1.plusNanos(time2.getNano());

        return time1;
    }

    public static LocalTime[] adjustElapsedTimes(LocalTime[] elapsedTimes) {
        LocalTime[] adjustedElapsedTimes = elapsedTimes.clone();

        // Adjust elapsedTime every time the delta in resultsInStage is less than 1 second
        for (int i = 1; i < elapsedTimes.length; i++) { // i = 1 to compare previous result against current result
            LocalTime previousTime = elapsedTimes[i - 1];
            LocalTime currentTime = elapsedTimes[i];

            long delta = previousTime.until(currentTime, ChronoUnit.SECONDS);
            if (delta < 1) {
                adjustedElapsedTimes[i] = adjustedElapsedTimes[i - 1];
            }
        }

        return adjustedElapsedTimes;
    }

    public static int[] toRiderIds(ArrayList<Result> resultInstances) {
        int size = resultInstances.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = resultInstances.get(i).getRider().getId();
        }

        return result;
    }

    public static int[] toStageIds(ArrayList<Result> resultInstances) {
        int size = resultInstances.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = resultInstances.get(i).getStage().getId();
        }

        return result;
    }

    public static LocalTime[] toElapsedTimes(ArrayList<Result> resultInstances) {
        int size = resultInstances.size();

        LocalTime[] elapsedTimes = new LocalTime[size];

        for (int i = 0; i < size; i++) {
            elapsedTimes[i] = resultInstances.get(i).getElapsedTime();
        }

        return elapsedTimes;
    }

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
        return timeDelta(startTime, endTime);
    }

    public LocalTime getElapsedTimeToCheckpoint(Checkpoint checkpoint) {
        // TODO validate checkpointIndex
        int checkpointIndex = this.stage.getCheckpoints().indexOf(checkpoint);

        LocalTime startTime = this.checkpointTimes[0];
        LocalTime checkpointTime = this.checkpointTimes[checkpointIndex + 1];
        return timeDelta(startTime, checkpointTime);
    }

    public String toString(){
        return String.format("Result[stage=%s, rider=%s, checkpointTimes=%s]", this.stage, this.rider, this.checkpointTimes);
    }
}
