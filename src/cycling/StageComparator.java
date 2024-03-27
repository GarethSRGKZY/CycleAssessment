package cycling;

import java.util.Comparator;

/**
 * CheckpointComparator is a helper class to sort StageComparator chronologically based on their start times.
 * 
 * @author 730049785
 * @author 730099588
 * @version 1.0
 * 
 */
public class StageComparator implements Comparator<Stage> {
    @Override
    public int compare(Stage stage1, Stage stage2) {
        return stage1.getStartTime().compareTo(stage2.getStartTime());
    }
}
