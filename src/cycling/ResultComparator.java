package cycling;

import java.util.Comparator;

public class ResultComparator implements Comparator<Result> {
    // Static Attributes
    private int checkpointIndex;

    // Instance Methods
    public ResultComparator(int checkpointIndex) { // Constructor
        this.checkpointIndex = checkpointIndex;
    }

    public ResultComparator() { // Constructor (Overloaded)
        this(-1);
    }

    @Override
    public int compare(Result result1, Result result2) {
        if (this.checkpointIndex == -1) {
            return result1.getElapsedTime().compareTo(result2.getElapsedTime());
        }
        return result1.getElapsedTimeToCheckpoint(checkpointIndex).compareTo(result2.getElapsedTimeToCheckpoint(checkpointIndex));
    }
}
