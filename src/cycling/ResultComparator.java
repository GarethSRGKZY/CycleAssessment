package cycling;

import java.util.Comparator;

public class ResultComparator implements Comparator<Result> {
    @Override
    public int compare(Result result1, Result result2) {
        return result1.getElapsedTime().compareTo(result2.getElapsedTime());
    }
}
