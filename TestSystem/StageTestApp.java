import cycling.IllegalNameException;
import cycling.InvalidLengthException;
import cycling.InvalidNameException;
import cycling.StageType;
import cycling.CyclingPortal;
import cycling.CyclingPortalImpl;
import cycling.IDNotRecognisedException;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Month;

public class StageTestApp {
    public static void main(String[] args) throws IllegalNameException, InvalidNameException, InvalidLengthException, IDNotRecognisedException{
        CyclingPortal portal = new CyclingPortalImpl();
        LocalDateTime rightNow1 = LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40);
        LocalDateTime rightNow2 = LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 50);
        int raceId = portal.createRace("A", "Blabla");
        int stageId1 = portal.addStageToRace(raceId, "eplpeflpe", "fefef", 20, rightNow2, StageType.TT);
        int stageId2 = portal.addStageToRace(raceId, "eplpeflpe", "fefef", 20, rightNow1, StageType.TT);
        System.out.println(portal.getRaceStages(raceId) [0]);
        System.out.println(portal.getRaceStages(raceId) [1]);
    }
}
