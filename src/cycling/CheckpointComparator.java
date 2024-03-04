package cycling;

import java.util.Comparator;

public class CheckpointComparator implements Comparator<Checkpoint> {
    @Override
    public int compare(Checkpoint checkpoint1, Checkpoint checkpoint2) {
        return Double.compare(checkpoint1.getLocation(), checkpoint2.getLocation());
    }
}