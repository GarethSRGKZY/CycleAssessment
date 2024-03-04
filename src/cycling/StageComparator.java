package cycling;

import java.util.Comparator;

public class StageComparator implements Comparator<Stage> {
    @Override
    public int compare(Stage stage1, Stage stage2) {
        return stage1.startTime().compareTo(stage2.startTime());
    }
}
