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
    // Instance Attributes
    private ArrayList<Race> raceInstances;
	private ArrayList<Team> teamInstances;

    // Instance Methods
    public CyclingPortalImpl() { // Constructor
        this.raceInstances = new ArrayList<>();
		this.teamInstances = new ArrayList<>();
    }

    private Race findRace(int raceId) throws IDNotRecognisedException {
        Race race = Race.findRaceById(this.raceInstances, raceId);
        return race;
    }

    private Race findRaceContainsStage(int stageId) throws IDNotRecognisedException {
        for (Race race : this.raceInstances) {
            try {
                Stage stage = Stage.findStageById(race.getStages(), stageId);
                if (stage instanceof Stage) {
                    return race;
                }
            } catch (IDNotRecognisedException e) {
                continue;
            }
        }

        throw new IDNotRecognisedException(String.format("Stage id %d not found", stageId));
    }

    private Stage findStage(int stageId) throws IDNotRecognisedException {
        for (Race race : this.raceInstances) {
            try {
                return Stage.findStageById(race.getStages(), stageId);
            } catch (IDNotRecognisedException e) {
                continue;
            }
        }

        throw new IDNotRecognisedException(String.format("Stage id %d not found", stageId));
    }

    private Stage findStageContainsCheckpoint(int checkpointId) throws IDNotRecognisedException {
        for (Race race : this.raceInstances) {
            for (Stage stage : race.getStages()) {
                try {
                    Checkpoint checkpoint = Checkpoint.findCheckpointById(stage.getCheckpoints(), checkpointId);
                    if (checkpoint instanceof Checkpoint) {
                        return stage;
                    }
                } catch (IDNotRecognisedException e) {
                    continue;
                }
            }
        }

        throw new IDNotRecognisedException(String.format("Checkpoint id %d not found", checkpointId));
    }

    private Checkpoint findCheckpoint(int checkpointId) throws IDNotRecognisedException {
        for (Race race : this.raceInstances) {
            for (Stage stage : race.getStages()) {
                try {
                    return Checkpoint.findCheckpointById(stage.getCheckpoints(), checkpointId);
                } catch (IDNotRecognisedException e) {
                    continue;
                }
            }
        }

        throw new IDNotRecognisedException(String.format("Checkpoint id %d not found", checkpointId));
    }

	private Team findTeam(int teamId) throws IDNotRecognisedException {
        Team team = Team.findTeamById(this.teamInstances, teamId);
        return team;
    }

	private Team findTeamContainsRider(int riderId) throws IDNotRecognisedException {
        for (Team team : this.teamInstances) {
            try {
                Rider rider = Rider.findRiderById(team.getRiders(), riderId);
                if (rider instanceof Rider) {
                    return team;
                }
            } catch (IDNotRecognisedException e) {
                continue;
            }
        }

        throw new IDNotRecognisedException(String.format("Rider id %d not found", riderId));
    }

	private Rider findRider(int riderId) throws IDNotRecognisedException {
        for (Team team : this.teamInstances) {
            try {
                return Rider.findRiderById(team.getRiders(), riderId);
            } catch (IDNotRecognisedException e) {
                continue;
            }
        }

        throw new IDNotRecognisedException(String.format("Rider id %d not found", riderId));
    }

    // Instance Methods (Interface)
	@Override
	public int[] getRaceIds() {
		return Race.toIds(this.raceInstances);
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        try {
            Race existingRace = Race.findRaceByName(this.raceInstances, name);

            if (existingRace instanceof Race) {
                assert this.raceInstances.contains(existingRace)
                    : "There should be a Race with the same name in this.raceInstances";

                throw new IllegalNameException(String.format("Race name %s already exists", name));
            }
        } catch (NameNotRecognisedException e) {
            // Do nothing - name is unique
        }
        
        Race race = new Race(name, description);

        assert !this.raceInstances.contains(race)
            : "There should not be any existing references to a brand new Race";

        this.raceInstances.add(race);

        assert this.raceInstances.contains(race)
            : "The new Race should have been appended to this.raceInstances";

		return race.getId();
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		Race race = findRace(raceId);
		return race.toString();
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		Race race = findRace(raceId);
        this.raceInstances.remove(race);

        assert this.raceInstances.contains(race)
            : "The Race selected for removal should exist in this.raceInstances";

        this.raceInstances.remove(race);

        assert !this.raceInstances.contains(race)
            : "There should not be any references to a removed Race";
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		Race race = findRace(raceId);
        return race.getStages().size();
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		Race race = findRace(raceId);

		Stage stage = new Stage(stageName, description, length, startTime, type);
        race.addStage(stage);

		return stage.getId();
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        Race race = findRace(raceId);

		ArrayList<Stage> stageInstances = race.getStages();
		Collections.sort(stageInstances, new StageComparator());

		return Stage.toIds(stageInstances);
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		Stage stage = findStage(stageId);
        return stage.getLength();
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
        Race race = findRaceContainsStage(stageId);
        Stage stage = findStage(stageId);

        race.removeStage(stage);
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
        Stage stage = findStage(stageId);

        Checkpoint checkpoint = new Checkpoint(location, type, averageGradient, length);
        stage.addCheckpoint(checkpoint);

        return checkpoint.getId();
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        Stage stage = findStage(stageId);

        Checkpoint checkpoint = new Checkpoint(location);
        stage.addCheckpoint(checkpoint);

        return checkpoint.getId();
	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
        Stage stage = findStageContainsCheckpoint(checkpointId);
        Checkpoint checkpoint = findCheckpoint(checkpointId);

        stage.removeCheckpoint(checkpoint);
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
        Stage stage = findStage(stageId);
        stage.concludePreparation();
	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
        Stage stage = findStage(stageId);
        return Checkpoint.toIds(stage.getCheckpoints());
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		try {
            Team existingTeam = Team.findTeamByName(this.teamInstances, name);

            if (existingTeam instanceof Team) {
                assert this.teamInstances.contains(existingTeam)
                    : "There should be a Team with the same name in this.teamInstances";

                throw new IllegalNameException(String.format("Team name %s already exists", name));
            }
        } catch (NameNotRecognisedException e) {
            // Do nothing - name is unique
        }
        
        Team team = new Team(name, description);

        assert !this.teamInstances.contains(team)
            : "There should not be any existing references to a brand new Team";

        this.teamInstances.add(team);

        assert this.teamInstances.contains(team)
            : "The new Team should have been appended to this.teamInstances";

		return team.getId();
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
