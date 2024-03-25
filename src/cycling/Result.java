package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Result implements Serializable {
    // Static Attributes

    // Static Methods
    public static Result findResultById(ArrayList<Result> results, int stageId, int riderId) throws IDNotRecognisedException {
        for (Result result : results) {
            if (result.getStage().getId() == stageId && result.getRider().getId() == riderId) {
                return result;    
            }
        }

        throw new IDNotRecognisedException(String.format("Stage id %d and Rider id %d not found", stageId, riderId));
    }
    public static ArrayList<Result> findResultsByStageId(ArrayList<Result> results, int stageId) {
        ArrayList<Result> resultsInStage = new ArrayList<>();

        for (Result result : results) {
            if (result.getStage().getId() == stageId) {
                resultsInStage.add(result);
            }
        }

        return resultsInStage;
    }
    public static ArrayList<Result> findResultsByRiderId(ArrayList<Result> results, int riderId) {
        ArrayList<Result> resultsInRider = new ArrayList<>();

        for (Result result : results) {
            if (result.getRider().getId() == riderId) {
                resultsInRider.add(result);
            }
        }

        return resultsInRider;
    }

    public static void eraseResults(ArrayList<Result> results) {
        results.clear();
    }

    public static LocalTime timeDelta(LocalTime time1, LocalTime time2) {
        time1.minusHours(time2.getHour());
        time1.minusMinutes(time2.getMinute());
        time1.minusSeconds(time2.getSecond());
        time1.minusNanos(time2.getNano());

        return time1;
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

    public static int[] toRiderIds(ArrayList<Result> results) {
        int size = results.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = results.get(i).getRider().getId();
        }

        return result;
    }

    public static int[] toStageIds(ArrayList<Result> results) {
        int size = results.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = results.get(i).getStage().getId();
        }

        return result;
    }

    public static LocalTime[] toElapsedTimes(ArrayList<Result> results) {
        int size = results.size();

        LocalTime[] elapsedTimes = new LocalTime[size];

        for (int i = 0; i < size; i++) {
            elapsedTimes[i] = results.get(i).getElapsedTime();
        }

        return elapsedTimes;
    }

    public static String toString(ArrayList<Result> results) {
        String[] resultStrings = new String[results.size()];

        for (int i = 0; i < results.size(); ++i) {
            resultStrings[i] = results.get(i).toString();
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
    public Result(Stage stage, Rider rider, LocalTime... checkpointTimes) throws InvalidCheckpointTimesException, InvalidStageStateException {
        if (checkpointTimes.length != stage.getCheckpoints().size() + 2) {
            throw new InvalidCheckpointTimesException(String.format("Length of checkpoints %d must be length of checkpoints in stage + 2 %d", checkpointTimes.length, stage.getCheckpoints().size() + 2));
        }
        assert checkpointTimes.length >= 2
            : "There should at least be a start time and end time in a result";

        if (stage.getState() != StageState.WAITING_FOR_RESULTS) {
            throw new InvalidStageStateException(String.format("Stage state cannot be %s", stage.getState())); 
        }


        for (int i = 1; i < checkpointTimes.length; i++) {
            if (checkpointTimes[i - 1].compareTo(checkpointTimes[i]) > 1) {
                throw new InvalidCheckpointTimesException(String.format("Checkpoint times %s and %s are not in chronological order", checkpointTimes[i - 1], checkpointTimes[i]));
            }
        }

        this.stage = stage;
        this.rider = rider;
        this.checkpointTimes = checkpointTimes;
    }

    public Stage getStage() {
        return stage;
    }

    public Rider getRider() {
        return rider;
    }

    public LocalTime[] getCheckpointTimes() {
        return checkpointTimes;
    }

    public LocalTime getElapsedTime() {
        LocalTime startTime = checkpointTimes[0];
        LocalTime endTime = checkpointTimes[checkpointTimes.length - 1];
        return timeDelta(startTime, endTime);
    }

    public LocalTime getElapsedTimeToCheckpoint(Checkpoint checkpoint) {
        // TODO validate checkpointIndex
        int checkpointIndex = stage.getCheckpoints().indexOf(checkpoint);

        LocalTime startTime = checkpointTimes[0];
        LocalTime checkpointTime = checkpointTimes[checkpointIndex + 1];
        return timeDelta(startTime, checkpointTime);
    }

    public String toString(){
        return String.format("Result[stage=%s, rider=%s, checkpointTimes=%s]", stage, rider, checkpointTimes);
    }
}
