package cycling;

public class StagePoints { // Complements (corresponds to) StageType.java
	public static final int[] FLAT_STAGE_POINTS = new int[]{50, 30, 20, 18, 16, 14, 13, 10, 8, 7, 6, 5, 4, 3, 2};
	public static final int[] MEDIUM_MOUNTAIN_STAGE_POINTS = new int[]{30, 25, 22, 19, 17, 15, 13, 11, 9, 7, 6, 5, 4, 3, 2};
	public static final int[] HIGH_MOUNTAIN_STAGE_POINTS = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
	public static final int[] TT_STAGE_POINTS = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

	public static final int[] SPRINT_POINTS = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    public static int getStagePoints(StageType type, int index) {
        if (index >= 15) {
            return 0;
        }

        switch (type) {
            case FLAT:
                return FLAT_STAGE_POINTS[index];

            case MEDIUM_MOUNTAIN:
                return MEDIUM_MOUNTAIN_STAGE_POINTS[index];

            case HIGH_MOUNTAIN:
                return HIGH_MOUNTAIN_STAGE_POINTS[index];

            case TT:
                return TT_STAGE_POINTS[index];

            default:
                return 0;
        }
    }

    public static int getSprintPoints(int index) {
        if (index >= 15) {
            return 0;
        }
        return SPRINT_POINTS[index];
    }
}