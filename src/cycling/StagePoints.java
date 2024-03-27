package cycling;

/**
 * StagePoints is a helper class to determine the number of points that should be awarded to a rider
 * given their rank in a stage.
 * 
 * @author 730049785
 * @version 1.0
 * 
 */
public class StagePoints {
    // Complements (corresponds to) StageType.java
	public static final int[] FLAT_STAGE_POINTS = new int[]{50, 30, 20, 18, 16, 14, 13, 10, 8, 7, 6, 5, 4, 3, 2};
	public static final int[] MEDIUM_MOUNTAIN_STAGE_POINTS = new int[]{30, 25, 22, 19, 17, 15, 13, 11, 9, 7, 6, 5, 4, 3, 2};
	public static final int[] HIGH_MOUNTAIN_STAGE_POINTS = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
	public static final int[] TT_STAGE_POINTS = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    // Complements (corresponds to) CheckpointType.java
	public static final int[] SPRINT_POINTS = new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    public static final int[] C4_POINTS = new int[]{1};
    public static final int[] C3_POINTS = new int[]{2, 1};
    public static final int[] C2_POINTS = new int[]{5, 3, 2, 1};
    public static final int[] C1_POINTS = new int[]{10, 8, 6, 4, 2, 1};
    public static final int[] HC_POINTS = new int[]{20, 15, 12, 10, 8, 6, 4, 2};

    /**
     * Calculates the number of points awarded for crossing the finish line of a Stage.
     * 
     * @param type        Type of the Stage.
     * @param rankOfRider Integer representing the Rider's position in the race. (Starting from rank 0.)
     * @return            Integer representing the points awarded.
     */
    public static int getStagePoints(StageType type, int rankOfRider) {
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

        if (rankOfRider >= pointsToAwardRanked.length) {
            return 0;
        }
        return pointsToAwardRanked[rankOfRider];
    }

    /**
     * Calculates the number of points awarded for crossing a sprint Checkpoint.
     * 
     * @param rankOfRider Integer representing the Rider's position in the race. (Starting from rank 0.)
     * @return            Integer representing the points awarded.
     */
    public static int getSprintPoints(int rankOfRider) {
        if (rankOfRider >= SPRINT_POINTS.length) {
            return 0;
        }
        return SPRINT_POINTS[rankOfRider];
    }

    /**
     * Calculates the number of points awarded for crossing a mountain Checkpoint.
     * @param type        Type of the Checkpoint.
     * @param rankOfRider Integer representing the Rider's position in the race. (Starting from rank 0.)
     * @return            Integer representing the points awarded.
     */
    public static int getMountainPoints(CheckpointType type, int rankOfRider) {
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

        if (rankOfRider >= pointsToAwardRanked.length) {
            return 0;
        }
        return pointsToAwardRanked[rankOfRider];
    }
}