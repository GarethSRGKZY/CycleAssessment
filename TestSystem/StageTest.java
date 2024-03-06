import java.time.LocalDateTime;

import cycling.CyclingPortal;
import cycling.CyclingPortalImpl;
import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.InvalidLengthException;
import cycling.InvalidNameException;
import cycling.StageType;

public class StageTest {
    public static void main(String[] args) {
        StageTest test = new StageTest();
        test.test();
    }

    public void test() {
        System.out.println(testGetNumberOfStages() + 
        " - testGetNumberOfStages");
        System.out.println(testAddStageToRace() + 
        " - testAddStageToRace");
        System.out.println(testGetRaceStages() + 
        " - testGetRaceStages");
        System.out.println(testAddStageToRaceNameClash() + 
        " - testAddStageToRaceNameClash");
        System.out.println(testAddStageToRaceNameNull() + 
        " - testAddStageToRaceNameNull");
        System.out.println(testAddStageToRaceNameEmpty() + 
        " - testAddStageToRaceNameEmpty");
        System.out.println(testAddStageToRaceName30Chars() + 
        " - testAddStageToRaceName30Chars");
        System.out.println(testAddStageToRaceName31Chars() + 
        " - testAddStageToRaceName31Chars");
        System.out.println(testAddStageToRaceNameWhiteSpace() + 
        " - testAddStageToRaceNameWhiteSpace");
        System.out.println(testAddStageToRaceLength4() +
        " - testAddStageToRaceLength4");
        System.out.println(testAddStageToRaceLength5() +
        " - testAddStageToRaceLength5");
        System.out.println(testGetStageLength() + 
        " - testGetStageLength");
        System.out.println(testRemoveStageById() + 
        " - testRemoveStageById");
    }

    public boolean testGetNumberOfStages() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        int raceIdA;
        try {
            raceIdA = portal.createRace("A", "Race A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }

        condition = (portal.getRaceIds().length == 1);
        assert (condition)
            : "the Race should have been created";
        if (!condition) {
            return false;
        }

        int stageCount;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (stageCount == 0);
        assert (condition)
            : "the Race should not contain any Stages";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddStageToRace() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        int raceIdA;
        try {
            raceIdA = portal.createRace("A", "Race A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }

        int stageCount;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            assert false
                : "the Race should have been created";
            return false;
        }

        condition = (stageCount == 0);
        assert (condition)
            : "the Race should not contain any Stages";
        if (!condition) {
            return false;
        }

        int stageIdA;
        try {
            stageIdA = portal.addStageToRace(raceIdA, "A", "Stage A", 10, LocalDateTime.now(), StageType.FLAT);
        } catch (
                IDNotRecognisedException |
                IllegalNameException |
                InvalidNameException |
                InvalidLengthException e) {
            e.printStackTrace();
            return false;
        }

        int[] stageIds;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
            stageIds = portal.getRaceStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (stageCount == 1);
        assert (condition)
            : "the Race should contain one Stage";
        if (!condition) {
            return false;
        }

        condition = (stageCount == stageIds.length);
        assert (condition)
            : "the stageCount should match the length of stageIds";
        if (!condition) {
            return false;
        }

        condition = (stageIds[0] == stageIdA);
        assert (condition)
        : "the Race should contain one Stage with ID consistent with ID returned from createStage";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddStageToRaceNameClash() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        int raceIdA;
        try {
            raceIdA = portal.createRace("A", "Race A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }

        int stageCount;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            assert false
                : "the Race should have been created";
            return false;
        }

        condition = (stageCount == 0);
        assert (condition)
            : "the Race should not contain any Stages";
        if (!condition) {
            return false;
        }

        try {
            portal.addStageToRace(raceIdA, "A", "Stage A", 10, LocalDateTime.now(), StageType.FLAT);
            portal.addStageToRace(raceIdA, "A", "Stage A", 10, LocalDateTime.now(), StageType.FLAT);
            assert false
                : "expected IllegalNameException but got none";
            return false;
        } catch (
                IDNotRecognisedException |
                InvalidNameException |
                InvalidLengthException e) {
            e.printStackTrace();
            assert false
                : "expected IllegalNameException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (IllegalNameException e) {
            // Do nothing
        }

        int[] stageIds;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
            stageIds = portal.getRaceStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (stageCount == 1);
        assert (condition)
            : "the second Stage should not have been created";
        if (!condition) {
            return false;
        }

        condition = (stageCount == stageIds.length);
        assert (condition)
            : "the stageCount should match the length of stageIds";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddStageToRaceNameNull() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        int raceIdA;
        try {
            raceIdA = portal.createRace("A", "Race A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }
        
        int stageCount;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            assert false
            : "the Race should have been created";
            return false;
        }
        
        condition = (stageCount == 0);
        assert (condition)
        : "the Race should not contain any Stages";
        if (!condition) {
            return false;
        }
        
        try {
            portal.addStageToRace(raceIdA, null, "Stage A", 10, LocalDateTime.now(), StageType.FLAT);
            assert false
                : "expected InvalidNameException but got none";
            return false;
        } catch (
                IDNotRecognisedException |
                IllegalNameException |
                InvalidLengthException e) {
            e.printStackTrace();
            assert false
                : "expected InvalidNameException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (InvalidNameException e) {
            // Do nothing
        }

        int[] stageIds;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
            stageIds = portal.getRaceStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (stageCount == 0);
        assert (condition)
            : "the Stage should not have been created";
        if (!condition) {
            return false;
        }

        condition = (stageCount == stageIds.length);
        assert (condition)
            : "the stageCount should match the length of stageIds";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddStageToRaceNameEmpty() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        int raceIdA;
        try {
            raceIdA = portal.createRace("A", "Race A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }
        
        int stageCount;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            assert false
            : "the Race should have been created";
            return false;
        }
        
        condition = (stageCount == 0);
        assert (condition)
        : "the Race should not contain any Stages";
        if (!condition) {
            return false;
        }
        
        try {
            portal.addStageToRace(raceIdA, "Stage A", "Stage A", 10, LocalDateTime.now(), StageType.FLAT);
            assert false
                : "expected InvalidNameException but got none";
            return false;
        } catch (
                IDNotRecognisedException |
                IllegalNameException |
                InvalidLengthException e) {
            e.printStackTrace();
            assert false
                : "expected InvalidNameException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (InvalidNameException e) {
            // Do nothing
        }

        int[] stageIds;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
            stageIds = portal.getRaceStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (stageCount == 0);
        assert (condition)
            : "the Stage should not have been created";
        if (!condition) {
            return false;
        }

        condition = (stageCount == stageIds.length);
        assert (condition)
            : "the stageCount should match the length of stageIds";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddStageToRaceName30Chars() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        int raceIdA;
        try {
            raceIdA = portal.createRace("A", "Race A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }
        
        int stageCount;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            assert false
            : "the Race should have been created";
            return false;
        }
        
        condition = (stageCount == 0);
        assert (condition)
        : "the Race should not contain any Stages";
        if (!condition) {
            return false;
        }
        
        try {
            portal.addStageToRace(raceIdA, "123456789_123456789_123456789_", "Stage A", 10, LocalDateTime.now(), StageType.FLAT);
        } catch (
                IDNotRecognisedException |
                IllegalNameException |
                InvalidNameException |
                InvalidLengthException e) {
            e.printStackTrace();
            return false;
        }

        int[] stageIds;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
            stageIds = portal.getRaceStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (stageCount == 1);
        assert (condition)
            : "the Stage should have been created";
        if (!condition) {
            return false;
        }

        condition = (stageCount == stageIds.length);
        assert (condition)
            : "the stageCount should match the length of stageIds";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddStageToRaceName31Chars() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        int raceIdA;
        try {
            raceIdA = portal.createRace("A", "Race A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }
        
        int stageCount;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            assert false
            : "the Race should have been created";
            return false;
        }
        
        condition = (stageCount == 0);
        assert (condition)
        : "the Race should not contain any Stages";
        if (!condition) {
            return false;
        }
        
        try {
            portal.addStageToRace(raceIdA, "1234567890_1234567890_1234567890_1", "Stage A", 10, LocalDateTime.now(), StageType.FLAT);
            assert false
                : "expected InvalidNameException but got none";
            return false;
        } catch (
                IDNotRecognisedException |
                IllegalNameException |
                InvalidLengthException e) {
            e.printStackTrace();
            assert false
                : "expected InvalidNameException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (InvalidNameException e) {
            // Do nothing
        }

        int[] stageIds;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
            stageIds = portal.getRaceStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (stageCount == 0);
        assert (condition)
            : "the Stage should not have been created";
        if (!condition) {
            return false;
        }

        condition = (stageCount == stageIds.length);
        assert (condition)
            : "the stageCount should match the length of stageIds";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddStageToRaceNameWhiteSpace() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        int raceIdA;
        try {
            raceIdA = portal.createRace("A", "Race A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }
        
        int stageCount;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            assert false
            : "the Race should have been created";
            return false;
        }
        
        condition = (stageCount == 0);
        assert (condition)
        : "the Race should not contain any Stages";
        if (!condition) {
            return false;
        }
        
        try {
            portal.addStageToRace(raceIdA, "Stage A", "Stage A", 10, LocalDateTime.now(), StageType.FLAT);
            assert false
                : "expected InvalidNameException but got none";
            return false;
        } catch (
                IDNotRecognisedException |
                IllegalNameException | 
                InvalidLengthException e) {
            e.printStackTrace();
            assert false
                : "expected InvalidNameException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (InvalidNameException e) {
            // Do nothing
        }

        int[] stageIds;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
            stageIds = portal.getRaceStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (stageCount == 0);
        assert (condition)
            : "the Stage should not have been created";
        if (!condition) {
            return false;
        }

        condition = (stageCount == stageIds.length);
        assert (condition)
            : "the stageCount should match the length of stageIds";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddStageToRaceLength4() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        int raceIdA;
        try {
            raceIdA = portal.createRace("A", "Race A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }
        
        int stageCount;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            assert false
            : "the Race should have been created";
            return false;
        }
        
        condition = (stageCount == 0);
        assert (condition)
        : "the Race should not contain any Stages";
        if (!condition) {
            return false;
        }
        
        try {
            portal.addStageToRace(raceIdA, "A", "Stage A", 4, LocalDateTime.now(), StageType.FLAT);
            assert false
                : "expected InvalidLengthException but got none";
            return false;
        } catch (
                IDNotRecognisedException |
                IllegalNameException | 
                InvalidNameException e) {
            e.printStackTrace();
            assert false
                : "expected InvalidLengthException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (InvalidLengthException e) {
            // Do nothing
        }

        int[] stageIds;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
            stageIds = portal.getRaceStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (stageCount == 0);
        assert (condition)
            : "the Stage should not have been created";
        if (!condition) {
            return false;
        }

        condition = (stageCount == stageIds.length);
        assert (condition)
            : "the stageCount should match the length of stageIds";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddStageToRaceLength5() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        int raceIdA;
        try {
            raceIdA = portal.createRace("A", "Race A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }
        
        int stageCount;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            assert false
            : "the Race should have been created";
            return false;
        }
        
        condition = (stageCount == 0);
        assert (condition)
        : "the Race should not contain any Stages";
        if (!condition) {
            return false;
        }
        
        try {
            portal.addStageToRace(raceIdA, "A", "Stage A", 5, LocalDateTime.now(), StageType.FLAT);
        } catch (
                IDNotRecognisedException |
                IllegalNameException |
                InvalidNameException |
                InvalidLengthException e) {
            e.printStackTrace();
            return false;
        }

        int[] stageIds;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
            stageIds = portal.getRaceStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (stageCount == 1);
        assert (condition)
            : "the Stage should have been created";
        if (!condition) {
            return false;
        }

        condition = (stageCount == stageIds.length);
        assert (condition)
            : "the stageCount should match the length of stageIds";
        if (!condition) {
            return false;
        }

        return true;
    }

    // TODO add tests for checking stages sorted by time
    public boolean testGetRaceStages() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        int raceIdA;
        try {
            raceIdA = portal.createRace("A", "Race A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }

        int stageCount;
        int[] stageIds;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
            stageIds = portal.getRaceStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            assert false
                : "the Race should have been created";
            return false;
        }

        condition = (stageCount == 0);
        assert (condition)
            : "the Race should not contain any Stages";
        if (!condition) {
            return false;
        }

        condition = (stageCount == stageIds.length);
        assert (condition)
            : "the stageCount should match the length of stageIds";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testGetStageLength() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        double length = 10;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        int raceIdA;
        try {
            raceIdA = portal.createRace("A", "Race A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }
        
        int stageCount;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            assert false
            : "the Race should have been created";
            return false;
        }
        
        condition = (stageCount == 0);
        assert (condition)
        : "the Race should not contain any Stages";
        if (!condition) {
            return false;
        }
        
        int stageIdA;
        int[] stageIds;
        double stageLength;
        try {
            stageIdA = portal.addStageToRace(raceIdA, "A", "Stage A", length, LocalDateTime.now(), StageType.FLAT);
            stageCount = portal.getNumberOfStages(raceIdA);
            stageIds = portal.getRaceStages(raceIdA);
            stageLength = portal.getStageLength(stageIdA);
        } catch (
                IDNotRecognisedException |
                IllegalNameException |
                InvalidNameException |
                InvalidLengthException e) {
            e.printStackTrace();
            return false;
        }

        condition = (stageCount == 1);
        assert (condition)
            : "the Stage should have been created";
        if (!condition) {
            return false;
        }

        condition = (stageCount == stageIds.length);
        assert (condition)
            : "the stageCount should match the length of stageIds";
        if (!condition) {
            return false;
        }

        condition = (stageLength == length);
        assert (condition)
            : "the stageLength should match the length passed when calling createStage";

        return true;
    }

    public boolean testRemoveStageById() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);

        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        int raceIdA;
        try {
            raceIdA = portal.createRace("A", "Race A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }

        int stageCount;
        try {
            stageCount = portal.getNumberOfStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            assert false
                : "the Race should have been created";
            return false;
        }

        condition = (stageCount == 0);
        assert (condition)
            : "the Race should not contain any Stages";
        if (!condition) {
            return false;
        }

        int stageIdA;
        int[] stageIds;
        try {
            stageIdA = portal.addStageToRace(raceIdA, "A", "Stage A", 10, LocalDateTime.now(), StageType.FLAT);
            stageCount = portal.getNumberOfStages(raceIdA);
            stageIds = portal.getRaceStages(raceIdA);
        } catch (
                IDNotRecognisedException |
                IllegalNameException |
                InvalidNameException |
                InvalidLengthException e) {
            e.printStackTrace();
            return false;
        }

        condition = (stageCount == 1);
        assert (condition)
            : "the Race should contain one Stage";
        if (!condition) {
            return false;
        }

        condition = (stageCount == stageIds.length);
        assert (condition)
            : "the stageCount should match the length of stageIds";
        if (!condition) {
            return false;
        }
        
        try {
            portal.removeStageById(stageIdA);
            stageCount = portal.getNumberOfStages(raceIdA);
            stageIds = portal.getRaceStages(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (stageCount == 0);
        assert (condition)
            : "the Stage should have been removed";
        if (!condition) {
            return false;
        }

        condition = (stageCount == stageIds.length);
        assert (condition)
            : "the stageCount should match the length of stageIds";
        if (!condition) {
            return false;
        }

        return true;
    }
}