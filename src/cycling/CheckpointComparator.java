package cycling;

import java.util.Comparator;

/**
 * CheckpointComparator is a helper class to sort Checkpoints based on their location.
 * 
 * @author 730049785
 * @author 730099588
 * @version 1.0
 * 
 */
public class CheckpointComparator implements Comparator<Checkpoint> {
    @Override
    public int compare(Checkpoint checkpoint1, Checkpoint checkpoint2) {
        return Double.compare(checkpoint1.getLocation(), checkpoint2.getLocation());
    }
}