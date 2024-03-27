package cycling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * CyclingPortal is a compiling, fully functioning implementor
 * of the CyclingPortal interface.
 * 
 * @author 730049785
 * @author 730099588
 * @version 1.0
 *
 */
public class CyclingPortalImpl implements CyclingPortal {
    // Instance Attributes
    private ArrayList<Race> racesInPortal;
	private ArrayList<Team> teamsInPortal;
	private ArrayList<Result> resultsInPortal;

    // Instance Methods

    /**
     * Constructs an instance of the CyclingPortalImpl with no races, teams and results initialised
     */
    public CyclingPortalImpl() { // Constructor
        racesInPortal = new ArrayList<>();
		teamsInPortal = new ArrayList<>();
		resultsInPortal = new ArrayList<>();
    }

    // Instance Methods (Interface)
	@Override
	public int[] getRaceIds() {
		return Race.toIds(racesInPortal);
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
        try {
            Race existingRace = Race.findRaceByName(racesInPortal, name);

            if (existingRace instanceof Race) {
                assert racesInPortal.contains(existingRace)
                    : "There should be a Race with the same name in racesInPortal";

                throw new IllegalNameException(String.format("Race name %s already exists", name));
            }
        } catch (NameNotRecognisedException e) {
            // Do nothing - name is unique
        }
        
        Race race = new Race(name, description);

        assert !racesInPortal.contains(race)
            : "There should not be any existing references to a brand new Race";

        racesInPortal.add(race);

        assert racesInPortal.contains(race)
            : "The new Race should have been appended to racesInPortal";

		return race.getId();
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		Race race = Race.findRaceById(racesInPortal, raceId);
		return race.toString();
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		Race race = Race.findRaceById(racesInPortal, raceId);

        // Remove all Results referring to any Stage in the given Race
        for (Stage stage : race.getStages()) {
            race.removeStage(resultsInPortal, stage);
        }

        assert racesInPortal.contains(race)
            : "The Race selected for removal should exist in racesInPortal";

        racesInPortal.remove(race);

        assert !racesInPortal.contains(race)
            : "There should not be any references to a removed Race";
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		Race race = Race.findRaceById(racesInPortal, raceId);
        return race.getStages().size();
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		Race race = Race.findRaceById(racesInPortal, raceId);

		Stage stage = new Stage(stageName, description, length, startTime, type);
        race.addStage(racesInPortal, stage);

		return stage.getId();
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
        Race race = Race.findRaceById(racesInPortal, raceId);

		ArrayList<Stage> stagesInRace = race.getStages();
		Collections.sort(stagesInRace, new StageComparator());

		return Stage.toIds(stagesInRace);
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		Stage stage = Stage.findStageByIdFromRaces(racesInPortal, stageId);
        return stage.getLength();
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
        Race race = Race.findRaceContainsStageId(racesInPortal, stageId);
        Stage stage = Stage.findStageByIdFromRaces(racesInPortal, stageId);

        race.removeStage(resultsInPortal, stage);
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
        Stage stage = Stage.findStageByIdFromRaces(racesInPortal, stageId);

        Checkpoint checkpoint = new Checkpoint(location, type, averageGradient, length);
        stage.addCheckpoint(checkpoint);

        return checkpoint.getId();
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
        Stage stage = Stage.findStageByIdFromRaces(racesInPortal, stageId);

        Checkpoint checkpoint = new Checkpoint(location);
        stage.addCheckpoint(checkpoint);

        return checkpoint.getId();
	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
        Stage stage = Stage.findStageContainsCheckpoint(racesInPortal, checkpointId);
        Checkpoint checkpoint = Checkpoint.findCheckpointByIdFromRaces(racesInPortal, checkpointId);

        stage.removeCheckpoint(checkpoint);
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
        Stage stage = Stage.findStageByIdFromRaces(racesInPortal, stageId);
        stage.concludePreparation();
	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
        Stage stage = Stage.findStageByIdFromRaces(racesInPortal, stageId);

        ArrayList<Checkpoint> checkpointsInStage = stage.getCheckpoints();
        Collections.sort(checkpointsInStage, new CheckpointComparator());

        return Checkpoint.toIds(checkpointsInStage);
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		try {
            Team existingTeam = Team.findTeamByName(teamsInPortal, name);

            if (existingTeam instanceof Team) {
                assert teamsInPortal.contains(existingTeam)
                    : "There should be a Team with the same name in teamsInPortal";

                throw new IllegalNameException(String.format("Team name %s already exists", name));
            }
        } catch (NameNotRecognisedException e) {
            // Do nothing - name is unique
        }
        
        Team team = new Team(name, description);

        assert !teamsInPortal.contains(team)
            : "There should not be any existing references to a brand new Team";

        teamsInPortal.add(team);

        assert teamsInPortal.contains(team)
            : "The new Team should have been appended to teamsInPortal";

		return team.getId();
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		Team team = Team.findTeamById(teamsInPortal, teamId);

        // Remove all Results referring to any Rider in the given Team
        for (Rider rider : team.getRiders()) {
            team.removeRider(resultsInPortal, rider);
        }

        assert teamsInPortal.contains(team)
            : "The Team selected for removal should exist in teamsInPortal";

        teamsInPortal.remove(team);

        assert !teamsInPortal.contains(team)
            : "There should not be any references to a removed Team";
	}

	@Override
	public int[] getTeams() {
		return Team.toIds(teamsInPortal);
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		Team team = Team.findTeamById(teamsInPortal, teamId);

		ArrayList<Rider> ridersInTeam = team.getRiders();

		return Rider.toIds(ridersInTeam);
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		Team team = Team.findTeamById(teamsInPortal, teamID);

		Rider rider = new Rider(name, yearOfBirth);
		team.addRider(rider);

		return rider.getId();
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		Team team = Team.findTeamContainsRider(teamsInPortal, riderId);
        Rider rider = Rider.findRiderByIdFromTeams(teamsInPortal, riderId);

        team.removeRider(resultsInPortal, rider);
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
		try {
			Result existingResult = Result.findResultById(resultsInPortal, stageId, riderId);

			if (existingResult instanceof Result) {
				assert resultsInPortal.contains(existingResult)
					: "There should be a Result with the same stageId and riderId in resultsInPortal";

				throw new DuplicatedResultException(String.format("Result with stageId %d and riderId %d already exists", stageId, riderId));
			}
		} catch (IDNotRecognisedException e) {
			// Do nothing - name is unique
		}				
				
		Stage stage = Stage.findStageByIdFromRaces(racesInPortal, stageId);
		Rider rider = Rider.findRiderByIdFromTeams(teamsInPortal, riderId);
        Result result = new Result(stage, rider, checkpoints);

        assert !resultsInPortal.contains(result)
            : "There should not be any existing references to a brand new Result";

        resultsInPortal.add(result);

        assert resultsInPortal.contains(result)
            : "The new Result should have been appended to resultsInPortal";
	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		Result result = Result.findResultById(resultsInPortal, stageId, riderId);

		int n = result.getStage().getCheckpoints().size();
		LocalTime[] timesInResult = new LocalTime[n + 1]; // +1 for storing the elapsed time in the last index

        // Extract the times in the result to an array, index 0 to n - 1
		for (int i = 0; i < n; i++) {
			timesInResult[i] = result.getCheckpointTimes()[i + 1]; // i + 1, since index 0 stores the startTime
		}

        // Set the last index (n) to elapsed time
		timesInResult[n] = result.getElapsedTime();

		return timesInResult;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
        ArrayList<Result> resultsInStage = Result.findResultsByStageId(resultsInPortal, stageId);

        // Rank results by elapsedTime
        Collections.sort(resultsInStage, new ResultComparator());

        // Extract the elapsedTime of each result
        LocalTime[] elapsedTimesRanked = Result.toElapsedTimes(resultsInStage);

        LocalTime[] elapsedTimesRankedAdjusted = Result.adjustElapsedTimes(elapsedTimesRanked);

        Result result = Result.findResultById(resultsInStage, stageId, riderId);
        int rankOfRider = resultsInStage.indexOf(result);

		return elapsedTimesRankedAdjusted[rankOfRider];
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		Result result = Result.findResultById(resultsInPortal, stageId, riderId);
        
        assert resultsInPortal.contains(result)
            : "The Result selected for removal should exist in resultsInPortal";
        
        resultsInPortal.remove(result);

        assert !resultsInPortal.contains(result)
            : "There should not be any references to a removed Result";
	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
        ArrayList<Result> resultsInStage = Result.findResultsByStageId(resultsInPortal, stageId);

        // Rank results by elapsedTime
        Collections.sort(resultsInStage, new ResultComparator());

        return Result.toRiderIds(resultsInStage);
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
        ArrayList<Result> resultsInStage = Result.findResultsByStageId(resultsInPortal, stageId);

        // Rank results by elapsedTime
        Collections.sort(resultsInStage, new ResultComparator());

        // Extract the elapsedTime of each result
        LocalTime[] elapsedTimesRanked = Result.toElapsedTimes(resultsInStage);

        LocalTime[] elapsedTimesRankedAdjusted = Result.adjustElapsedTimes(elapsedTimesRanked);

        return elapsedTimesRankedAdjusted;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
        ArrayList<Result> resultsInStage = Result.findResultsByStageId(resultsInPortal, stageId);
        Stage stage = Stage.findStageByIdFromRaces(racesInPortal, stageId);

        HashMap<Integer, Integer> riderIdToPoints = new HashMap<>();

        // Calculate & award points for each SPRINT Checkpoint
        for (Checkpoint checkpoint : stage.getCheckpoints()) {
            // Ignore checkpoint if it isn't SPRINT
            if (checkpoint.getType() != CheckpointType.SPRINT) {
                continue;
            }

            // Rank results by elapsedTimeToCheckpoint
            Collections.sort(resultsInStage, new ResultComparator(checkpoint));

            int rankOfRider = 0;
            for (int riderId : Result.toRiderIds(resultsInStage)) {
                int points = StagePoints.getSprintPoints(rankOfRider);
                // Award points to rider
                if (riderIdToPoints.containsKey(riderId)) {
                    riderIdToPoints.put(riderId, riderIdToPoints.get(riderId) + points);
                } else {
                    riderIdToPoints.put(riderId, points);
                }
                rankOfRider++;
            }
            assert rankOfRider == resultsInStage.size();
        }

        assert riderIdToPoints.size() == resultsInStage.size();

        // Calculate & award points for finish line
        // Rank results by elapsedTime
        int[] riderIdsRankedByElapsedTime = getRidersRankInStage(stageId);
        int[] pointsRanked = new int[riderIdsRankedByElapsedTime.length];

        assert riderIdsRankedByElapsedTime.length == riderIdToPoints.size();

        int rankOfRider = 0;
        for (int riderId : riderIdsRankedByElapsedTime) {
            int points = StagePoints.getStagePoints(stage.getType(), rankOfRider);
            // Award points to rider
            pointsRanked[rankOfRider] = riderIdToPoints.get(riderId) + points;
            rankOfRider++;
        }
        assert rankOfRider == riderIdsRankedByElapsedTime.length;

		return pointsRanked;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
        ArrayList<Result> resultsInStage = Result.findResultsByStageId(resultsInPortal, stageId);
        Stage stage = Stage.findStageByIdFromRaces(racesInPortal, stageId);

        HashMap<Integer, Integer> riderIdToPoints = new HashMap<>();

        // Calculate & award points for each MOUNTAIN Checkpoint
        for (Checkpoint checkpoint : stage.getCheckpoints()) {
            // Ignore checkpoint if it isn't C1, C2, C3, C4, HC
            if (checkpoint.getType() == CheckpointType.SPRINT) {
                continue;
            }

            // Rank results by elapsedTimeToCheckpoint
            Collections.sort(resultsInStage, new ResultComparator(checkpoint));

            int rankOfRider = 0;
            for (int riderId : Result.toRiderIds(resultsInStage)) {
                int points = StagePoints.getMountainPoints(checkpoint.getType(), rankOfRider);
                // Award points to rider
                if (riderIdToPoints.containsKey(riderId)) {
                    riderIdToPoints.put(riderId, riderIdToPoints.get(riderId) + points);
                } else {
                    riderIdToPoints.put(riderId, points);
                }
                rankOfRider++;
            }
            assert rankOfRider == resultsInStage.size();
        }

        assert riderIdToPoints.size() == resultsInStage.size();

        // Calculate & award points for finish line
        // Rank results by elapsedTime
        int[] riderIdsRankedByElapsedTime = getRidersRankInStage(stageId);
        int[] pointsRanked = new int[riderIdsRankedByElapsedTime.length];

        assert riderIdsRankedByElapsedTime.length == riderIdToPoints.size();

        int rankOfRider = 0;
        for (int riderId : riderIdsRankedByElapsedTime) {
            int points = StagePoints.getStagePoints(stage.getType(), rankOfRider);
            // Award points to rider
            pointsRanked[rankOfRider] = riderIdToPoints.get(riderId) + points;
            rankOfRider++;
        }
        assert rankOfRider == riderIdsRankedByElapsedTime.length;

		return pointsRanked;
	}

	@Override
	public void eraseCyclingPortal() {
        Race.eraseRaces(racesInPortal);
        Team.eraseTeams(teamsInPortal);
        Result.eraseResults(resultsInPortal);

        Stage.eraseStages();
        Checkpoint.eraseCheckpoints();
        Rider.eraseRiders();
	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
        try (
            FileOutputStream fileOutput = new FileOutputStream(filename);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
        ) {
            objectOutput.writeObject(this);
        }
	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
        CyclingPortalImpl portal;
		try (
            FileInputStream fileInput = new FileInputStream(filename);
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
        ) {
            Object objectRead = objectInput.readObject();

            if (objectRead instanceof CyclingPortalImpl) {
                portal = (CyclingPortalImpl) objectRead;
                racesInPortal = portal.racesInPortal;
                teamsInPortal = portal.teamsInPortal;
                resultsInPortal = portal.resultsInPortal;
                
                Race.loadRaces(racesInPortal);
                for (Race race : racesInPortal) {
                    ArrayList<Stage> stagesInRace = race.getStages();

                    Stage.loadStages(stagesInRace);
                    for (Stage stage : stagesInRace) {
                        ArrayList<Checkpoint> checkpointsInStage = stage.getCheckpoints();

                        Checkpoint.loadCheckpoints(checkpointsInStage);
                    }
                }

                Team.loadTeams(teamsInPortal);
                for (Team team : teamsInPortal) {
                    ArrayList<Rider> ridersInTeam = team.getRiders();

                    Rider.loadRiders(ridersInTeam);
                }
            }
        }
	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		Race race = Race.findRaceByName(racesInPortal, name);

        // Remove all Results referring to any Stage in the given Race
        for (Stage stage : race.getStages()) {
            race.removeStage(resultsInPortal, stage);
        }

        assert racesInPortal.contains(race)
            : "The Race selected for removal should exist in racesInPortal";

        racesInPortal.remove(race);

        assert !racesInPortal.contains(race)
            : "There should not be any references to a removed Race";
	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		int[] stageIdsInRace = getRaceStages(raceId);
        
        HashMap<Integer, LocalTime> riderIdToTimeSum = new HashMap<>();

        for (int stageId : stageIdsInRace) {
            int[] riderIdsRankedByElapsedTime = getRidersRankInStage(stageId);
            LocalTime[] elapsedTimesRankedAdjusted = getRankedAdjustedElapsedTimesInStage(stageId);

            assert riderIdsRankedByElapsedTime.length == elapsedTimesRankedAdjusted.length;

            int rankOfRider = 0;
            for (int riderId : riderIdsRankedByElapsedTime) {
                LocalTime elapsedTime = elapsedTimesRankedAdjusted[rankOfRider];

                // Increment TimeSum
                if (riderIdToTimeSum.containsKey(riderId)) {
                    LocalTime existingElapsedTime = riderIdToTimeSum.get(riderId);
                    riderIdToTimeSum.put(riderId, Result.timeAdd(elapsedTime, existingElapsedTime));
                } else {
                    riderIdToTimeSum.put(riderId, elapsedTime);
                }
                rankOfRider++;
            }
            assert rankOfRider == riderIdsRankedByElapsedTime.length;
        }

        ArrayList<LocalTime> timeSums = new ArrayList<>(riderIdToTimeSum.values());
        Collections.sort(timeSums);

        LocalTime[] timeSumsRanked = timeSums.toArray(new LocalTime[timeSums.size()]);

        return timeSumsRanked;
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		int[] stageIdsInRace = getRaceStages(raceId);
        
        HashMap<Integer, Integer> riderIdToPointSum = new HashMap<>();

        for (int stageId : stageIdsInRace) {
            int[] riderIdsRankedByElapsedTime = getRidersRankInStage(stageId);
            int[] pointsRankedByElapsedTime = getRidersPointsInStage(stageId);

            assert riderIdsRankedByElapsedTime.length == pointsRankedByElapsedTime.length;

            int rankOfRider = 0;
            for (int riderId : riderIdsRankedByElapsedTime) {
                int points = pointsRankedByElapsedTime[rankOfRider];

                // Award points to rider
                if (riderIdToPointSum.containsKey(riderId)) {
                    int existingPoints = riderIdToPointSum.get(riderId);
                    riderIdToPointSum.put(riderId, points + existingPoints);
                } else {
                    riderIdToPointSum.put(riderId, points);
                }
                rankOfRider++;
            }
            assert rankOfRider == riderIdsRankedByElapsedTime.length;
        }

        // Convert riderIdsRankedByTimeSum to pointSumsRankedByTimeSum using riderIdToPointSum map
        int[] riderIdsRankedByTimeSum = getRidersGeneralClassificationRank(raceId);
        int[] pointSumsRankedByTimeSum = new int[riderIdsRankedByTimeSum.length];

        assert riderIdsRankedByTimeSum.length == riderIdToPointSum.size();

        int rankOfRider = 0;
        for (int riderId : riderIdsRankedByTimeSum) {
            pointSumsRankedByTimeSum[rankOfRider] = riderIdToPointSum.get(riderId);
            rankOfRider++;
        }
        assert rankOfRider == riderIdsRankedByTimeSum.length;
        
        return pointSumsRankedByTimeSum;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		int[] stageIdsInRace = getRaceStages(raceId);
        
        HashMap<Integer, Integer> riderIdToPointSum = new HashMap<>();

        for (int stageId : stageIdsInRace) {
            int[] riderIdsRankedByElapsedTime = getRidersRankInStage(stageId);
            int[] mountainPointsRankedByElapsedTime = getRidersMountainPointsInStage(stageId);

            assert riderIdsRankedByElapsedTime.length == mountainPointsRankedByElapsedTime.length;

            int rankOfRider = 0;
            for (int riderId : riderIdsRankedByElapsedTime) {
                int mountainPoints = mountainPointsRankedByElapsedTime[rankOfRider];

                // Award points to rider
                if (riderIdToPointSum.containsKey(riderId)) {
                    int existingMountainPoints = riderIdToPointSum.get(riderId);
                    riderIdToPointSum.put(riderId, mountainPoints + existingMountainPoints);
                } else {
                    riderIdToPointSum.put(riderId, mountainPoints);
                }
                rankOfRider++;
            }
            assert rankOfRider == riderIdsRankedByElapsedTime.length;
        }

        // Convert riderIdsRankedByTimeSum to pointSumsRankedByTimeSum using riderIdToPointSum map
        int[] riderIdsRankedByTimeSum = getRidersGeneralClassificationRank(raceId);
        int[] pointSumsRankedByTimeSum =  new int[riderIdsRankedByTimeSum.length];

        assert riderIdsRankedByTimeSum.length == riderIdToPointSum.size();

        int rankOfRider = 0;
        for (int riderId : riderIdsRankedByTimeSum) {
            pointSumsRankedByTimeSum[rankOfRider] = riderIdToPointSum.get(riderId);
            rankOfRider++;
        }
        assert rankOfRider == riderIdsRankedByTimeSum.length;
        
        return pointSumsRankedByTimeSum;
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		int[] stageIdsInRace = getRaceStages(raceId);
        
        HashMap<Integer, LocalTime> riderIdToTimeSum = new HashMap<>();

        for (int stageId : stageIdsInRace) {
            int[] riderIdsRankedByElapsedTime = getRidersRankInStage(stageId);
            LocalTime[] elapsedTimesRankedAdjusted = getRankedAdjustedElapsedTimesInStage(stageId);

            assert riderIdsRankedByElapsedTime.length == elapsedTimesRankedAdjusted.length;

            int rankOfRider = 0;
            for (int riderId : riderIdsRankedByElapsedTime) {
                LocalTime elapsedTime = elapsedTimesRankedAdjusted[rankOfRider];

                // Increment TimeSum
                if (riderIdToTimeSum.containsKey(riderId)) {
                    LocalTime existingElapsedTime = riderIdToTimeSum.get(riderId);
                    riderIdToTimeSum.put(riderId, Result.timeAdd(elapsedTime, existingElapsedTime));
                } else {
                    riderIdToTimeSum.put(riderId, elapsedTime);
                }
                rankOfRider++;
            }
            assert rankOfRider == riderIdsRankedByElapsedTime.length;
        }

        // Sort riderToTimeSum by timeSum (Sort map entries by value)
        ArrayList<HashMap.Entry<Integer, LocalTime>> riderIdToTimeSumEntries = new ArrayList<>(riderIdToTimeSum.entrySet());
        Collections.sort(riderIdToTimeSumEntries,
            new Comparator<HashMap.Entry<Integer, LocalTime>>() {
                public int compare(HashMap.Entry<Integer, LocalTime> entry1, HashMap.Entry<Integer, LocalTime> entry2) {
                    return entry1.getValue().compareTo(entry2.getValue());
                }
            }
        );

        int[] riderIdsRankedByTimeSum = new int[riderIdToTimeSumEntries.size()];
        int rankOfRider = 0;
        for (HashMap.Entry<Integer, LocalTime> entry : riderIdToTimeSumEntries) {
            int riderId = entry.getKey();
            riderIdsRankedByTimeSum[rankOfRider] = riderId;
            rankOfRider++;
        }
        assert rankOfRider == riderIdsRankedByTimeSum.length;

        return riderIdsRankedByTimeSum;
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
        int[] riderIdsRankedByTimeSum = getRidersGeneralClassificationRank(raceId);
        int[] pointSumsRankedByTimeSum = getRidersPointsInRace(raceId);

        assert riderIdsRankedByTimeSum.length == pointSumsRankedByTimeSum.length;

        HashMap<Integer, Integer> riderIdToPointSum = new HashMap<>();
        
        int rankOfRider = 0;
        for (int riderId : riderIdsRankedByTimeSum) {
            int points = pointSumsRankedByTimeSum[rankOfRider];
            riderIdToPointSum.put(riderId, points);
            rankOfRider++;
        }
        assert rankOfRider == riderIdsRankedByTimeSum.length;

        ArrayList<HashMap.Entry<Integer, Integer>> riderIdToPointSumEntries = new ArrayList<>();

        Collections.sort(riderIdToPointSumEntries,
            new Comparator<HashMap.Entry<Integer, Integer>>() {
                public int compare(HashMap.Entry<Integer, Integer> entry1, HashMap.Entry<Integer, Integer> entry2) {
                    return entry2.getValue().compareTo(entry1.getValue()); // Descending order
                }
            }
        );

        int[] riderIdsRankedByPointSum = new int[riderIdToPointSumEntries.size()];

        rankOfRider = 0;
        for (HashMap.Entry<Integer, Integer> entry : riderIdToPointSumEntries) {
            int riderId = entry.getKey();
            riderIdsRankedByPointSum[rankOfRider] = riderId;
            rankOfRider++;
        }
        assert rankOfRider == riderIdToPointSumEntries.size();

		return riderIdsRankedByPointSum;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
        int[] riderIdsRankedByTimeSum = getRidersGeneralClassificationRank(raceId);
        int[] pointSumsRankedByTimeSum = getRidersMountainPointsInRace(raceId);

        assert riderIdsRankedByTimeSum.length == pointSumsRankedByTimeSum.length;

        HashMap<Integer, Integer> riderIdToPointSum = new HashMap<>();
        
        int rankOfRider = 0;
        for (int riderId : riderIdsRankedByTimeSum) {
            int points = pointSumsRankedByTimeSum[rankOfRider];
            riderIdToPointSum.put(riderId, points);
            rankOfRider++;
        }
        assert rankOfRider == riderIdsRankedByTimeSum.length;

        ArrayList<HashMap.Entry<Integer, Integer>> riderIdToPointSumEntries = new ArrayList<>();

        Collections.sort(riderIdToPointSumEntries,
            new Comparator<HashMap.Entry<Integer, Integer>>() {
                public int compare(HashMap.Entry<Integer, Integer> entry1, HashMap.Entry<Integer, Integer> entry2) {
                    return entry2.getValue().compareTo(entry1.getValue()); // Descending order
                }
            }
        );

        int[] riderIdsRankedByPointSum = new int[riderIdToPointSumEntries.size()];

        rankOfRider = 0;
        for (HashMap.Entry<Integer, Integer> entry : riderIdToPointSumEntries) {
            int riderId = entry.getKey();
            riderIdsRankedByPointSum[rankOfRider] = riderId;
            rankOfRider++;
        }
        assert rankOfRider == riderIdToPointSumEntries.size();

		return riderIdsRankedByPointSum;
	}

}
