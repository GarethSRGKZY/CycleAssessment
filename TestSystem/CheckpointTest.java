import java.time.LocalDateTime;

import cycling.CheckpointType;
import cycling.CyclingPortal;
import cycling.CyclingPortalImpl;
import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.InvalidLengthException;
import cycling.InvalidLocationException;
import cycling.InvalidNameException;
import cycling.InvalidStageStateException;
import cycling.InvalidStageTypeException;
import cycling.StageType;

public class CheckpointTest {
    public static void main(String[] args) {
        CheckpointTest test = new CheckpointTest();
        test.test();
    }

    public void test() {
        System.out.println(testAddCategorizedClimbToStage() +
        " - testAddCategorizedClimbToStage");
        System.out.println(testAddCategorizedClimbToStageLocationEqualsLength() +
        " - testAddCategorizedClimbToStageLocationEqualsLength");
        System.out.println(testAddCategorizedClimbToStageLocationExceedsLength() +
        " - testAddCategorizedClimbToStageLocationExceedsLength");
        System.out.println(testAddCategorizedClimbToStageLocationEquals0() +
        " - testAddCategorizedClimbToStageLocationEquals0");
        System.out.println(testAddCategorizedClimbToStageLocationEquals1() +
        " - testAddCategorizedClimbToStageLocationEquals1");
        System.out.println(testAddCategorizedClimbToStageStateEqualsWaiting() +
        " - testAddCategorizedClimbToStageStateEqualsWaiting");
        System.out.println(testAddCategorizedClimbToStageTypeEqualsTimeTrial() +
        " - testAddCategorizedClimbToStageTypeEqualsTimeTrial");
        System.out.println(testAddIntermediateSprintToStage() +
        " - testAddIntermediateSprintToStage");
        System.out.println(testAddIntermediateSprintToStageLocationEqualsLength() +
        " - testAddIntermediateSprintToStageLocationEqualsLength");
        System.out.println(testAddIntermediateSprintToStageLocationExceedsLength() +
        " - testAddIntermediateSprintToStageLocationExceedsLength");
        System.out.println(testAddIntermediateSprintToStageLocationEquals0() +
        " - testAddIntermediateSprintToStageLocationEquals0");
        System.out.println(testAddIntermediateSprintToStageLocationEquals1() +
        " - testAddIntermediateSprintToStageLocationEquals1");
        System.out.println(testAddIntermediateSprintToStageStateEqualsWaiting() +
        " - testAddIntermediateSprintToStageStateEqualsWaiting");
        System.out.println(testAddIntermediateSprintToStageTypeEqualsTimeTrial() +
        " - testAddIntermediateSprintToStageTypeEqualsTimeTrial");
        System.out.println(testRemoveCheckpoint() +
        " - testRemoveCheckpoint");
        System.out.println(testRemoveCheckpointStateEqualsWaiting() +
        " - testRemoveCheckpointStateEqualsWaiting");
        System.out.println(testConcludeStagePreparation() +
        " - testConcludeStagePreparation");
        System.out.println(testGetStageCheckpoints() +
        " - testGetStageCheckpoints");
    }

    public boolean testAddCategorizedClimbToStage() {
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }

        int checkpointId;
        try {
            checkpointId = portal.addCategorizedClimbToStage(stageIdA, 3.0, CheckpointType.C1, 1.0, 1.0);
        } catch (
            IDNotRecognisedException |
            InvalidLocationException |
            InvalidStageStateException |
            InvalidStageTypeException e
        ) {
            e.printStackTrace();
            return false;
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 1);
        assert (condition)
            : "the Stage should contain one Checkpoint";
        if (!condition) {
            return false;
        }

        condition = (checkpointIds[0] == checkpointId);
        assert (condition)
        : "the Stage should contain one Checkpoint with ID consistent with ID returned from addCategorizedClimbToStage";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddCategorizedClimbToStageLocationEqualsLength() {
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
        try {
            stageIdA = portal.addStageToRace(raceIdA, "A", "Stage A", length, LocalDateTime.now(), StageType.FLAT);
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }

        int checkpointId;
        try {
            checkpointId = portal.addCategorizedClimbToStage(stageIdA, length, CheckpointType.C1, 1.0, 1.0);
        } catch (
            IDNotRecognisedException |
            InvalidLocationException |
            InvalidStageStateException |
            InvalidStageTypeException e
        ) {
            e.printStackTrace();
            return false;
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 1);
        assert (condition)
            : "the Stage should contain one Checkpoint";
        if (!condition) {
            return false;
        }

        condition = (checkpointIds[0] == checkpointId);
        assert (condition)
        : "the Stage should contain one Checkpoint with ID consistent with ID returned from addCategorizedClimbToStage";
        if (!condition) {
            return false;
        }

        return true;
    }
    
    public boolean testAddCategorizedClimbToStageLocationExceedsLength() {
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
        try {
            stageIdA = portal.addStageToRace(raceIdA, "A", "Stage A", length, LocalDateTime.now(), StageType.FLAT);
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }

        try {
            portal.addCategorizedClimbToStage(stageIdA, length + 1, CheckpointType.C1, 1.0, 1.0);
            assert false
                : "expected InvalidLocationException but got none";
            return false;
        } catch (
            IDNotRecognisedException |
            InvalidStageStateException |
            InvalidStageTypeException e
        ) {
            e.printStackTrace();
            assert false
                : "expected InvalidLocationException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (InvalidLocationException e) {
            // Do nothing
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Checkpoint should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddCategorizedClimbToStageLocationEquals0() {
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }

        try {
            portal.addCategorizedClimbToStage(stageIdA, 0.0, CheckpointType.C1, 1.0, 1.0);
            assert false
                : "expected InvalidLocationException but got none";
            return false;
        } catch (
            IDNotRecognisedException |
            InvalidStageStateException |
            InvalidStageTypeException e
        ) {
            e.printStackTrace();
            assert false
                : "expected InvalidLocationException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (InvalidLocationException e) {
            // Do nothing
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Checkpoint should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddCategorizedClimbToStageLocationEquals1() {
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }

        int checkpointId;
        try {
            checkpointId = portal.addCategorizedClimbToStage(stageIdA, 1.0, CheckpointType.C1, 1.0, 1.0);
        } catch (
            IDNotRecognisedException |
            InvalidLocationException |
            InvalidStageStateException |
            InvalidStageTypeException e
        ) {
            e.printStackTrace();
            return false;
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 1);
        assert (condition)
            : "the Stage should contain one Checkpoint";
        if (!condition) {
            return false;
        }

        condition = (checkpointIds[0] == checkpointId);
        assert (condition)
        : "the Stage should contain one Checkpoint with ID consistent with ID returned from addCategorizedClimbToStage";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddCategorizedClimbToStageStateEqualsWaiting() {
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }
        
        try {
            portal.concludeStagePreparation(stageIdA);
            portal.addCategorizedClimbToStage(stageIdA, 3.0, CheckpointType.C1, 1.0, 1.0);
            assert false
                : "expected InvalidStageStateException but got none";
            return false;
        } catch (
            IDNotRecognisedException |
            InvalidLocationException |
            InvalidStageTypeException e
        ) {
            e.printStackTrace();
            assert false
                : "expected InvalidStageStateException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (InvalidStageStateException e) {
            // Do nothing
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Checkpoint should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddCategorizedClimbToStageTypeEqualsTimeTrial() {
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
            stageIdA = portal.addStageToRace(raceIdA, "A", "Stage A", 10, LocalDateTime.now(), StageType.TT);
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }
        
        try {
            portal.addCategorizedClimbToStage(stageIdA, 3.0, CheckpointType.C1, 1.0, 1.0);
            assert false
                : "expected InvalidStageTypeException but got none";
            return false;
        } catch (
            IDNotRecognisedException |
            InvalidLocationException |
            InvalidStageStateException e
        ) {
            e.printStackTrace();
            assert false
                : "expected InvalidStageTypeException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (InvalidStageTypeException e) {
            // Do nothing
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Checkpoint should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddIntermediateSprintToStage() {
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }

        int checkpointId;
        try {
            checkpointId = portal.addIntermediateSprintToStage(stageIdA, 3.0);
        } catch (
            IDNotRecognisedException |
            InvalidLocationException |
            InvalidStageStateException |
            InvalidStageTypeException e
        ) {
            e.printStackTrace();
            return false;
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 1);
        assert (condition)
            : "the Stage should contain one Checkpoint";
        if (!condition) {
            return false;
        }

        condition = (checkpointIds[0] == checkpointId);
        assert (condition)
        : "the Stage should contain one Checkpoint with ID consistent with ID returned from addIntermediateSprintToStage";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddIntermediateSprintToStageLocationEqualsLength() {
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
        try {
            stageIdA = portal.addStageToRace(raceIdA, "A", "Stage A", length, LocalDateTime.now(), StageType.FLAT);
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }

        int checkpointId;
        try {
            checkpointId = portal.addIntermediateSprintToStage(stageIdA, length);
        } catch (
            IDNotRecognisedException |
            InvalidLocationException |
            InvalidStageStateException |
            InvalidStageTypeException e
        ) {
            e.printStackTrace();
            return false;
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 1);
        assert (condition)
            : "the Stage should contain one Checkpoint";
        if (!condition) {
            return false;
        }

        condition = (checkpointIds[0] == checkpointId);
        assert (condition)
        : "the Stage should contain one Checkpoint with ID consistent with ID returned from addIntermediateSprintToStage";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddIntermediateSprintToStageLocationExceedsLength() {
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
        try {
            stageIdA = portal.addStageToRace(raceIdA, "A", "Stage A", length, LocalDateTime.now(), StageType.FLAT);
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }

        try {
            portal.addIntermediateSprintToStage(stageIdA, length + 1);
            assert false
                : "expected InvalidLocationException but got none";
            return false;
        } catch (
            IDNotRecognisedException |
            InvalidStageStateException |
            InvalidStageTypeException e
        ) {
            e.printStackTrace();
            assert false
                : "expected InvalidLocationException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (InvalidLocationException e) {
            // Do nothing
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Checkpoint should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddIntermediateSprintToStageLocationEquals0() {
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }

        try {
            portal.addIntermediateSprintToStage(stageIdA, 0.0);
            assert false
                : "expected InvalidLocationException but got none";
            return false;
        } catch (
            IDNotRecognisedException |
            InvalidStageStateException |
            InvalidStageTypeException e
        ) {
            e.printStackTrace();
            assert false
                : "expected InvalidLocationException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (InvalidLocationException e) {
            // Do nothing
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Checkpoint should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddIntermediateSprintToStageLocationEquals1() {
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }

        int checkpointId;
        try {
            checkpointId = portal.addIntermediateSprintToStage(stageIdA, 1.0);
        } catch (
            IDNotRecognisedException |
            InvalidLocationException |
            InvalidStageStateException |
            InvalidStageTypeException e
        ) {
            e.printStackTrace();
            return false;
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 1);
        assert (condition)
            : "the Stage should contain one Checkpoint";
        if (!condition) {
            return false;
        }

        condition = (checkpointIds[0] == checkpointId);
        assert (condition)
        : "the Stage should contain one Checkpoint with ID consistent with ID returned from addIntermediateSprintToStage";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddIntermediateSprintToStageStateEqualsWaiting() {
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }
        
        try {
            portal.concludeStagePreparation(stageIdA);
            portal.addIntermediateSprintToStage(stageIdA, 3.0);
            assert false
                : "expected InvalidStageStateException but got none";
            return false;
        } catch (
            IDNotRecognisedException |
            InvalidLocationException |
            InvalidStageTypeException e
        ) {
            e.printStackTrace();
            assert false
                : "expected InvalidStageStateException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (InvalidStageStateException e) {
            // Do nothing
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Checkpoint should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testAddIntermediateSprintToStageTypeEqualsTimeTrial() {
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
            stageIdA = portal.addStageToRace(raceIdA, "A", "Stage A", 10, LocalDateTime.now(), StageType.TT);
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }
        
        try {
            portal.addIntermediateSprintToStage(stageIdA, 3.0);
            assert false
                : "expected InvalidStageTypeException but got none";
            return false;
        } catch (
            IDNotRecognisedException |
            InvalidLocationException |
            InvalidStageStateException e
        ) {
            e.printStackTrace();
            assert false
                : "expected InvalidStageTypeException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (InvalidStageTypeException e) {
            // Do nothing
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Checkpoint should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testRemoveCheckpoint() {
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }

        int checkpointIdA;
        int checkpointIdB;
        try {
            checkpointIdA = portal.addCategorizedClimbToStage(stageIdA, 3.0, CheckpointType.C1, 1.0, 1.0);
            checkpointIdB = portal.addIntermediateSprintToStage(stageIdA, 3.0);
        } catch (
            IDNotRecognisedException |
            InvalidLocationException |
            InvalidStageStateException |
            InvalidStageTypeException e
        ) {
            e.printStackTrace();
            return false;
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 2);
        assert (condition)
            : "the Stage should contain two Checkpoints";
        if (!condition) {
            return false;
        }

        condition = (checkpointIds[0] == checkpointIdA);
        assert (condition)
        : "the Stage should contain one Checkpoint (at index 0) with ID consistent with ID returned from addCategorizedClimbToStage";
        if (!condition) {
            return false;
        }

        condition = (checkpointIds[1] == checkpointIdB);
        assert (condition)
        : "the Stage should contain one Checkpoint (at index 1) with ID consistent with ID returned from addIntermediateSprintToStage";
        if (!condition) {
            return false;
        }

        try {
            portal.removeCheckpoint(checkpointIdA);
            portal.removeCheckpoint(checkpointIdB);
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (
            IDNotRecognisedException |
            InvalidStageStateException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testRemoveCheckpointStateEqualsWaiting() {
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }

        int checkpointIdA;
        int checkpointIdB;
        try {
            checkpointIdA = portal.addCategorizedClimbToStage(stageIdA, 3.0, CheckpointType.C1, 1.0, 1.0);
            checkpointIdB = portal.addIntermediateSprintToStage(stageIdA, 3.0);
        } catch (
            IDNotRecognisedException |
            InvalidLocationException |
            InvalidStageStateException |
            InvalidStageTypeException e
        ) {
            e.printStackTrace();
            return false;
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 2);
        assert (condition)
            : "the Stage should contain two Checkpoints";
        if (!condition) {
            return false;
        }

        condition = (checkpointIds[0] == checkpointIdA);
        assert (condition)
        : "the Stage should contain one Checkpoint (at index 0) with ID consistent with ID returned from addCategorizedClimbToStage";
        if (!condition) {
            return false;
        }

        condition = (checkpointIds[1] == checkpointIdB);
        assert (condition)
        : "the Stage should contain one Checkpoint (at index 1) with ID consistent with ID returned from addIntermediateSprintToStage";
        if (!condition) {
            return false;
        }

        try {
            portal.concludeStagePreparation(stageIdA);
            portal.removeCheckpoint(checkpointIdA);
            portal.removeCheckpoint(checkpointIdB);
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
            assert false
                : "expected InvalidStageStateException but got none";
            return false;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            assert false
                : "expected InvalidStageStateException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (InvalidStageStateException e) {
            // Do nothing
        }

        condition = (checkpointCount == 2);
        assert (condition)
            : "the Checkpoints should not have been removed";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testConcludeStagePreparation() {
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }

        try {
            portal.addCategorizedClimbToStage(stageIdA, 3.0, CheckpointType.C1, 1.0, 1.0);
            portal.addIntermediateSprintToStage(stageIdA, 3.0);
        } catch (
            IDNotRecognisedException |
            InvalidLocationException |
            InvalidStageStateException |
            InvalidStageTypeException e
        ) {
            e.printStackTrace();
            return false;
        }

        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 2);
        assert (condition)
            : "the Stage should contain two Checkpoints";
        if (!condition) {
            return false;
        }

        try {
            portal.concludeStagePreparation(stageIdA);
            portal.addCategorizedClimbToStage(stageIdA, 3.0, CheckpointType.C1, 1.0, 1.0);
            portal.addIntermediateSprintToStage(stageIdA, 3.0);
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
            assert false
                : "expected InvalidStageStateException but got none";
            return false;
        } catch (
            IDNotRecognisedException |
            InvalidLocationException |
            InvalidStageTypeException e) {
            e.printStackTrace();
            assert false
                : "expected InvalidStageStateException but got %s".formatted(e.getClass().getName());
            return false;
        } catch (InvalidStageStateException e) {
            // Do nothing
        }

        condition = (checkpointCount == 2);
        assert (condition)
            : "the Checkpoints should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testGetStageCheckpoints() {
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

        int checkpointCount;
        int[] checkpointIds;
        try {
            checkpointIds = portal.getStageCheckpoints(stageIdA);
            checkpointCount = checkpointIds.length;
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (checkpointCount == 0);
        assert (condition)
            : "the Stage should not contain any Checkpoints";
        if (!condition) {
            return false;
        }

        return true;
    }
}