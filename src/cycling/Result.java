package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Result is a class representing each Result corresponding to a unique Rider and Stage pair.
 * 
 * @author 730049785
 * @author 730099588
 * @version 1.0
 * 
 */
public class Result implements Serializable {
    // Static Attributes

    // Static Methods
    /**
     * Finds the first Result in a given list of Results with a matching stageId and riderId.
     * <p>
     * A helper method.
     * 
     * @param results ArrayList of Result objects to be searched through.
     * @param stageId ID of the Stage referenced in the Result being queried.
     * @param riderId ID of the Rider referenced in the Result being queried.
     * @return        A single Result object referencing the Stage matching the stageId
     *                and a Rider matching the riderId.
     * @throws IDNotRecognisedException If the stageId and riderId pair
     *                                  does not match to any Results in the given list.
     */
    public static Result findResultById(ArrayList<Result> results, int stageId, int riderId) throws IDNotRecognisedException {
        for (Result result : results) {
            if (result.getStage().getId() == stageId && result.getRider().getId() == riderId) {
                return result;    
            }
        }

        throw new IDNotRecognisedException(String.format("Stage id %d and Rider id %d not found", stageId, riderId));
    }

    /**
     * Finds all Results in a given list of Results with a matching stageId.
     * 
     * @param results ArrayList of Result objects to be searched through.
     * @param stageId ID of the Stage referenced in the Results being queried.
     * @return        ArrayList of Result objects referencing the Stage matching the stageId.
     */
    public static ArrayList<Result> findResultsByStageId(ArrayList<Result> results, int stageId) {
        ArrayList<Result> resultsInStage = new ArrayList<>();

        for (Result result : results) {
            if (result.getStage().getId() == stageId) {
                resultsInStage.add(result);
            }
        }

        return resultsInStage;
    }

    /**
     * Finds all Results in a given list of Results with a matching riderId.
     * 
     * @param results ArrayList of Result objects to be searched through.
     * @param riderId ID of the Rider referenced in the Results being queried.
     * @return        ArrayList of Result objects referencing the Rider matching the riderId.
     */
    public static ArrayList<Result> findResultsByRiderId(ArrayList<Result> results, int riderId) {
        ArrayList<Result> resultsInRider = new ArrayList<>();

        for (Result result : results) {
            if (result.getRider().getId() == riderId) {
                resultsInRider.add(result);
            }
        }

        return resultsInRider;
    }

    /**
     * Drops all stored references of Race instances from the given list of Races.
     * 
     * @param results ArrayList of Results that should be cleared.
     */
    public static void eraseResults(ArrayList<Result> results) {
        results.clear();
    }

    /**
     * Derives a new LocalTime representing the difference between the two given LocalTimes.
     * <p>
     * A helper method.
     * 
     * @param time1 Former LocalTime.
     * @param time2 Latter LocalTime.
     * @return      LocalTime representing the difference between time1 and time2.
     */
    public static LocalTime timeDelta(LocalTime time1, LocalTime time2) {
        time1.minusHours(time2.getHour());
        time1.minusMinutes(time2.getMinute());
        time1.minusSeconds(time2.getSecond());
        time1.minusNanos(time2.getNano());

        return time1;
    }

    /**
     * Derives a new LocalTime representing the sum of the two given LocalTimes.
     * <p>
     * A helper method.
     * 
     * @param time1 Former LocalTime.
     * @param time2 Latter LocalTime.
     * @return      LocalTime representing the sum of time1 and time2.
     */
    public static LocalTime timeAdd(LocalTime time1, LocalTime time2) {
        time1.plusHours(time2.getHour());
        time1.plusMinutes(time2.getMinute());
        time1.plusSeconds(time2.getSecond());
        time1.plusNanos(time2.getNano());

        return time1;
    }

    /**
     * Adjusts the latter elapsed time to be equal to the former if the difference is less than 1 second.
     * <p>
     * A helper method.
     * 
     * @param elapsedTimes Array of elapsed times.
     * @return             Array of elapsed times after adjustment.
     */
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

    /**
     * Converts a given list of Results into an array of its referenced Rider's ID.
     * <p>
     * A helper method.
     * 
     * @param results ArrayList of Result objects to be converted into IDs.
     * @return        Array of integers representing the ID of the Rider referenced by each Result in the given list.
     */
    public static int[] toRiderIds(ArrayList<Result> results) {
        int size = results.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = results.get(i).getRider().getId();
        }

        return result;
    }

    /**
     * Converts a given list of Results into an array of its referenced Stage's ID.
     * <p>
     * A helper method.
     * 
     * @param results ArrayList of Result objects to be converted into IDs.
     * @return        Array of integers representing the ID of the Stage referenced by each Result in the given list.
     */
    public static int[] toStageIds(ArrayList<Result> results) {
        int size = results.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = results.get(i).getStage().getId();
        }

        return result;
    }

    /**
     * Converts a given list of Results into an array of its corresponding Rider's elapsed time.
     * <p>
     * A helper method.
     * 
     * @param results ArrayList of Result objects to be converted into elapsed times.
     * @return        Array of LocalTimes representing the elapsed time of the Rider referenced by each Result.
     */
    public static LocalTime[] toElapsedTimes(ArrayList<Result> results) {
        int size = results.size();

        LocalTime[] elapsedTimes = new LocalTime[size];

        for (int i = 0; i < size; i++) {
            elapsedTimes[i] = results.get(i).getElapsedTime();
        }

        return elapsedTimes;
    }

    /**
     * Converts a given list of Results into a single string containing each Result's details.
     * <p>
     * A helper method.
     * 
     * @param results ArrayList of Result objects to be included in the string.
     * @return        A single string containing details of all Results in the given list.
     */
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
    /**
     * Constructs a Rider instance and validates its name and checkpoint times.
     * 
     * @param stage           The Stage the result refers to.
     * @param rider           The Rider the result refers to.
     * @param checkpointTimes An array of times at which the Rider reached each of the
	 *                        Checkpoints of the Stage, including the start time and the
	 *                        finish line.
     * @throws InvalidCheckpointTimesException If the length of checkpointTimes is
	 *                                         not equal to n+2, where n is the number
	 *                                         of Checkpoints in the stage; +2 represents
	 *                                         the start time and the finish time of the
	 *                                         stage.
     *                                         If the LocalTimes are not in chronological order.
     * @throws InvalidStageStateException      If the Stage is not "waiting for results".
	 *                                         (If the Stage's state field is StageState.PREPARING_STAGE.)
     */
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

    /**
     * Getter for the stage field of the current Result.
     * 
     * @return Stage the result refers to.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Getter for the rider field of the current Result.
     * 
     * @return Rider the result refers to.
     */
    public Rider getRider() {
        return rider;
    }

    /**
     * Getter for the checkpointTimes field of the current Result.
     * 
     * @return An array of times at which the Rider reached each of the
	 *         Checkpoints of the Stage, including the start time and the
	 *         finish line.
     */
    public LocalTime[] getCheckpointTimes() {
        return checkpointTimes;
    }

    /**
     * Getter for the elapsed time until the finish line of the current Result.
     * 
     * @return Elapsed time calculated from the checkpoint times.
     */
    public LocalTime getElapsedTime() {
        LocalTime startTime = checkpointTimes[0];
        LocalTime endTime = checkpointTimes[checkpointTimes.length - 1];
        return timeDelta(startTime, endTime);
    }

    /**
     * Getter for the elapsed time until the given Checkpoint of the current Result.
     * 
     * @param checkpoint The Checkpoint to refer to when calculating the elapsed time.
     * @return Elapsed time calculated from the checkpoint times.
     */
    public LocalTime getElapsedTimeToCheckpoint(Checkpoint checkpoint) {
        assert stage.getCheckpoints().contains(checkpoint);

        int checkpointIndex = stage.getCheckpoints().indexOf(checkpoint);

        LocalTime startTime = checkpointTimes[0];
        LocalTime checkpointTime = checkpointTimes[checkpointIndex + 1];
        return timeDelta(startTime, checkpointTime);
    }

    /**
     * Converts the current Result's details into a String.
     * 
     * @return String containing values for all fields of the Result.
     */
    public String toString(){
        return String.format("Result[stage=%s, rider=%s, checkpointTimes=%s]", stage, rider, checkpointTimes);
    }
}
