package cycling;

import java.util.Comparator;

public class ResultComparator implements Comparator<Result> {
    // Static Attributes
    private Checkpoint checkpoint;

    // Instance Methods
    public ResultComparator(Checkpoint checkpoint) { // Constructor
        this.checkpoint = checkpoint;
    }

    public ResultComparator() { // Constructor (Overloaded)
        this(null);
    }

    @Override
    public int compare(Result result1, Result result2) {
        if (this.checkpoint == null) {
            return result1.getElapsedTime().compareTo(result2.getElapsedTime());
        }
        return result1.getElapsedTimeToCheckpoint(checkpoint).compareTo(result2.getElapsedTimeToCheckpoint(checkpoint));
    }
}
