package cycling;

import java.util.Comparator;

/**
 * ResultComparator is a helper class to sort Results based on their elapsed times to 
 * either the finish line or a given Checkpoint.
 * 
 * @author 730049785
 * @author 730099588
 * @version 1.0
 * 
 */
public class ResultComparator implements Comparator<Result> {
    // Static Attributes
    private Checkpoint checkpoint;

    // Instance Methods
    /**
     * Constructs a CheckpointComparator instance.
     * Compares elapsed times to the given Checkpoint.
     * 
     * @param checkpoint The Checkpoint to refer to when calculating the elapsed time.
     */
    public ResultComparator(Checkpoint checkpoint) { // Constructor
        this.checkpoint = checkpoint;
    }

    /**
     * Constructs a CheckpointComparator instance.
     * Compares elapsed times to the finish line.
     */
    public ResultComparator() { // Constructor (Overloaded)
        this(null);
    }

    /**
     * {@inheritDoc}
     * 
     * Compares the elapsed times of the former and latter results.
     */
    @Override
    public int compare(Result result1, Result result2) {
        if (this.checkpoint == null) {
            return result1.getElapsedTime().compareTo(result2.getElapsedTime());
        }
        return result1.getElapsedTimeToCheckpoint(checkpoint).compareTo(result2.getElapsedTimeToCheckpoint(checkpoint));
    }
}
