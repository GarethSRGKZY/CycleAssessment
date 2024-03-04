import cycling.IllegalNameException;
import cycling.InvalidLengthException;
import cycling.InvalidLocationException;
import cycling.InvalidNameException;
import cycling.InvalidStageStateException;
import cycling.InvalidStageTypeException;
import cycling.IDNotRecognisedException;
import cycling.StageType;
import cycling.CyclingPortal;
import cycling.CyclingPortalImpl;

import java.time.LocalDateTime;
import java.time.Month;


public class CheckpointTestApp {
    public static void main(String[] args) throws IllegalNameException, InvalidNameException, InvalidLengthException, 
    InvalidLocationException, IDNotRecognisedException, InvalidStageStateException, InvalidStageTypeException{
        CyclingPortal portal = new CyclingPortalImpl();
        LocalDateTime rightNow2 = LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 50);
        int raceId = portal.createRace("A", "Blabla");
        int stageId1 = portal.addStageToRace(raceId, "eplpeflpe", "fefef", 20, rightNow2, StageType.TT);
        int checkpointId1 = portal.addIntermediateSprintToStage(stageId1, 5.0);
        int checkpointId2 = portal.addIntermediateSprintToStage(stageId1, 4.0);
        System.out.println(portal.getStageCheckpoints(stageId1) [0]);
        System.out.println(portal.getStageCheckpoints(stageId1) [1]);
    }
}
