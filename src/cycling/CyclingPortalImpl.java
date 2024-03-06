package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 * BadCyclingPortal is a minimally compiling, but non-functioning implementor
 * of the CyclingPortal interface.
 * 
 * @author Diogo Pacheco
 * @version 2.0
 *
 */
public class CyclingPortalImpl implements CyclingPortal {

    private ArrayList<Race> raceInstances = new ArrayList<>();

	@Override
	public int[] getRaceIds() {
		return Race.getRaceIds(raceInstances);
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		Race race = Race.createRace(raceInstances, name, description);
		return race.getId();
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		Race race = Race.getRaceById(raceInstances, raceId);
		return race.toString();
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		Race.removeRaceById(raceInstances, raceId);

	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		Race race = Race.getRaceById(raceInstances, raceId);
		ArrayList<Stage> stages = race.getStages();
		return stages.size();
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		Race race = Race.getRaceById(raceInstances, raceId);
        ArrayList<Stage> stageInstances = race.getStages();

		Stage stage = Stage.createStage(stageInstances, stageName, description, length, startTime, type);
		return stage.getId();
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		Race race = Race.getRaceById(raceInstances, raceId);
		ArrayList<Stage> stages = race.getStages();
		Collections.sort(stages, new StageComparator());
		return Stage.getStageIds(stages);
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		for (Race race : raceInstances) {
            ArrayList<Stage> stageInstances = race.getStages();
            
            try {
                Stage stage = Stage.getStageById(stageInstances, stageId);
                return stage.getLength();
            } catch (Exception IDNotRecognisedException) {
                // Stage does not belong to this race - Search through next race
                continue;
            }
        }
		throw new IDNotRecognisedException(String.format("Stage id %d not found", stageId));
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
        for (Race race : raceInstances) {
            ArrayList<Stage> stageInstances = race.getStages();
            
            try {
                Stage.removeStageById(stageInstances, stageId);
            } catch (Exception IDNotRecognisedException) {
                // Stage does not belong to this race - Search through next race
                continue;
            }
        }
        throw new IDNotRecognisedException(String.format("Stage id %d not found", stageId));
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
        for (Race race : raceInstances) {
            ArrayList<Stage> stageInstances = race.getStages();
            
            try {
                Stage stage = Stage.getStageById(stageInstances, stageId);

                ArrayList<Checkpoint> checkpointInstances = stage.getCheckpoints();
                Checkpoint checkpoint = Checkpoint.createCheckpoint(checkpointInstances, location, type, averageGradient, length);

                return checkpoint.getId();
            } catch (Exception IDNotRecognisedException) {
                // Stage does not belong to this race - Search through next race
                continue;
            }
        }
		throw new IDNotRecognisedException(String.format("Stage id %d not found", stageId));
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        for (Race race : raceInstances) {
            ArrayList<Stage> stageInstances = race.getStages();
            
            try {
                Stage stage = Stage.getStageById(stageInstances, stageId);

                ArrayList<Checkpoint> checkpointInstances = stage.getCheckpoints();
                Checkpoint checkpoint = Checkpoint.createCheckpoint(checkpointInstances, location);

                return checkpoint.getId();
            } catch (Exception IDNotRecognisedException) {
                // Stage does not belong to this race - Search through next race
                continue;
            }
        }
        throw new IDNotRecognisedException(String.format("Stage id %d not found", stageId));
	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
        for (Race race : raceInstances) {
            ArrayList<Stage> stageInstances = race.getStages();
            
            try {
                for (Stage stage: stageInstances) {
                    ArrayList<Checkpoint> checkpointInstances = stage.getCheckpoints();

                    try {
                        Checkpoint.removeCheckpointById(checkpointInstances, checkpointId);
                        return;
                    } catch (Exception IDNotRecognisedException) {
                        // Checkpoint does not belong to this stage - Search through next stage
                        continue;
                    }
                }

            } catch (Exception IDNotRecognisedException) {
                // Stage does not belong to this race - Search through next race
                continue;
            }
        }
		throw new IDNotRecognisedException(String.format("Checkpoint id %d not found", checkpointId));
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
        for (Race race : raceInstances) {
            ArrayList<Stage> stageInstances = race.getStages();
            
            try {
                Stage stage = Stage.getStageById(stageInstances, stageId);
                stage.concludePreparation();
                return;
            } catch (Exception IDNotRecognisedException) {
                // Stage does not belong to this race - Search through next race
                continue;
            }
        }
        throw new IDNotRecognisedException(String.format("Stage id %d not found", stageId));
	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
        for (Race race : raceInstances) {
            ArrayList<Stage> stageInstances = race.getStages();
            
            try {
                Stage stage = Stage.getStageById(stageInstances, stageId);

                ArrayList<Checkpoint> checkpointInstances = stage.getCheckpoints();
                Collections.sort(checkpointInstances, new CheckpointComparator());
				return Checkpoint.getCheckpointIds(checkpointInstances);
            } catch (Exception IDNotRecognisedException) {
                // Stage does not belong to this race - Search through next race
                continue;
            }
        }
        throw new IDNotRecognisedException(String.format("Stage id %d not found", stageId));
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getTeams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eraseCyclingPortal() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

}
