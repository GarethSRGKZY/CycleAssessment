package cycling;

public class StagePoints { // Complements (corresponds to) StageType.java
	public static final int[] FLAT_STAGE_POINTS = new int[]{50, 30, 20, 18, 16, 14, 13, 10, 8, 7, 6, 5, 4, 3, 2};
	public static final int[] MEDIUM_MOUNTAIN_STAGE_POINTS = new int[]{30, 25, 22, 19, 17, 15, 13, 11, 9, 7, 6, 5, 4, 3, 2};
	public static final int[] HIGH_MOUNTAIN_STAGE_POINTS = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
	public static final int[] TT_STAGE_POINTS = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

	public static final int[] SPRINT_POINTS = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    public static final int[] C4_POINTS = new int[]{1};
    public static final int[] C3_POINTS = new int[]{2, 1};
    public static final int[] C2_POINTS = new int[]{5, 3, 2, 1};
    public static final int[] C1_POINTS = new int[]{10, 8, 6, 4, 2, 1};
    public static final int[] HC_POINTS = new int[]{20, 15, 12, 10, 8, 6, 4, 2};

    public static int getStagePoints(StageType type, int index) {
        int[] pointsToAwardRanked;

        switch (type) {
            case FLAT:
                pointsToAwardRanked = FLAT_STAGE_POINTS;
                break;

            case MEDIUM_MOUNTAIN:
                pointsToAwardRanked = MEDIUM_MOUNTAIN_STAGE_POINTS;
                break;

            case HIGH_MOUNTAIN:
                pointsToAwardRanked = HIGH_MOUNTAIN_STAGE_POINTS;
                break;

            case TT:
                pointsToAwardRanked = TT_STAGE_POINTS;
                break;

            default:
                return 0;
        }

        if (index >= pointsToAwardRanked.length) {
            return 0;
        }
        return pointsToAwardRanked[index];
    }

    public static int getSprintPoints(int index) {
        if (index >= SPRINT_POINTS.length) {
            return 0;
        }
        return SPRINT_POINTS[index];
    }

    public static int getMountainPoints(CheckpointType type, int index) {
        assert type != CheckpointType.SPRINT;

        int[] pointsToAwardRanked;

        switch (type) {
            case C4:
                pointsToAwardRanked = C4_POINTS;
                break;

            case C3:
                pointsToAwardRanked = C3_POINTS;
                break;

            case C2:
                pointsToAwardRanked = C2_POINTS;
                break;

            case C1:
                pointsToAwardRanked = C1_POINTS;
                break;

            case HC:
                pointsToAwardRanked = HC_POINTS;
                break;

            default:
                return 0;
        }

        if (index >= pointsToAwardRanked.length) {
            return 0;
        }
        return pointsToAwardRanked[index];
    }
}