package cycling;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Team is a class representing each Team belonging to the CyclingPortal.
 * Each Team can own multiple Rider objects.
 * 
 * @author 730049785
 * @version 1.0
 * 
 */
public class Team implements Serializable {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    /**
     * Finds the first Team in a given list of Teams with a matching teamId.
     * <p>
     * A helper method.
     * 
     * @param teams  ArrayList of Team objects to be searched through.
     * @param teamId ID of the Team being queried.
     * @return       A single Team object with an id field that matches the given teamId.
     * @throws IDNotRecognisedException If the ID does not match to any Teams in the given list.
     */
    public static Team findTeamById(ArrayList<Team> teams, int teamId) throws IDNotRecognisedException {
        for (Team team : teams) {
            if (team.getId() == teamId) {
                return team;
            }
        }

        throw new IDNotRecognisedException(String.format("Team id %d not found", teamId));
    }

    /**
     * Finds the first Team in a given list of Teams that contain a Checkpoint with a matching checkpointId.
     * <p>
     * A helper method.
     * 
     * @param teams   ArrayList of Team objects to be searched through.
     * @param riderId ID of the Rider being queried.
     * @return        A single Team object containing a Rider
     *                with its id field matching the given riderId.
     * @throws IDNotRecognisedException If the riderId does not exist in any Teams in the given list.
     */
    public static Team findTeamContainsRider(ArrayList<Team> teams, int riderId) throws IDNotRecognisedException {
        for (Team team : teams) {
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

    /**
     * Finds the first Team in a given list of Teams with a matching teamName.
     * <p>
     * A helper method.
     * 
     * @param teams    ArrayList of Team objects to be searched through.
     * @param teamName Name of the Team being queried.
     * @return         A single Team object with a name field that matches the given teamName.
     * @throws NameNotRecognisedException If the name does not match to any Team in the given list.
     */
    public static Team findTeamByName(ArrayList<Team> teams, String teamName) throws NameNotRecognisedException {
        for (Team team : teams) {
            if (team.getName().equals(teamName)) {
                return team;
            }
        }

        throw new NameNotRecognisedException(String.format("Team name %s not found", teamName));
    }

    /**
     * Drops all stored references of Race instances from the given list of Races
     * and resets the internal nextId counter of the Race class.
     * 
     * @param teams ArrayList of Teams that should be cleared.
     */
    public static void eraseTeams(ArrayList<Team> teams) {
        teams.clear();
        nextId = 0;
    }

    /**
     * Reads a given list of Teams for updating the nextId counter to prevent teamId clashes.
     * 
     * @param teams ArrayList of Team objects that should be registered in the system.
     */
    public static void loadTeams(ArrayList<Team> teams) {
        int[] teamIds = toIds(teams);

        for (int teamId : teamIds) {
            if (teamId >= nextId) {
                nextId = teamId + 1;
            }
        }
    }

    /**
     * Converts a given list of Teams into an array of its IDs.
     * <p>
     * A helper method.
     * 
     * @param teams ArrayList of Team objects to be converted into IDs.
     * @return      Array of integers representing each Team's ID in the given list.
     */
    public static int[] toIds(ArrayList<Team> teams) {
        int size = teams.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = teams.get(i).getId();
        }

        return result;
    }

    /**
     * Converts a given list of Teams into a single string containing each Team's details.
     * <p>
     * A helper method.
     * 
     * @param teams ArrayList of Team objects to be included in the string.
     * @return      A single string containing details of all Teams in the given list.
     */
    public static String toString(ArrayList<Team> teams) {
        String[] teamStrings = new String[teams.size()];

        for (int i = 0; i < teams.size(); ++i) {
            teamStrings[i] = teams.get(i).toString();
        }

        String result = "{";

        result += String.join(", ", teamStrings);

        result += "}";

        return result;
    }



    // Instance Attributes
    private int id;
    private ArrayList<Rider> riders;

    private String name;
    private String description;

    // Instance Methods
    /**
     * Constructs a Team instance and validates its name.
     * 
     * @param name        An identifier name for the Team.
     * @param description A descriptive text for the Team.
     * @throws InvalidNameException If the name is null, empty, has more than 30
	 *                              characters, or has white spaces.
     */
    public Team(String name, String description) throws InvalidNameException {
        if (name == null) {
            throw new InvalidNameException("Team name is null");
        }

        if (name.isEmpty()) {
            throw new InvalidNameException("Team name is empty");
        }

        if (name.length() > 30) {
            throw new InvalidNameException(String.format("Team name has more than 30 characters (%d)", name.length()));
        }
        
        if (name.contains(" ")) {
            throw new InvalidNameException(String.format("Team name %s contains spaces", name));
        }

        id = nextId++;
        riders = new ArrayList<>();

        this.name = name;
        this.description = description;
    }

    /**
     * Getter for the name field of the current Team.
     * 
     * @return Name of the current Team.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the riders field of the current Team.
     * 
     * @return ArrayList of Riders belonging to the current Team.
     */
    public ArrayList<Rider> getRiders() {
        return riders;
    }

    /**
     * Adds a Rider to the current Team.
     * 
     * @param rider Rider object to be added to the current Team.
     */
    public void addRider(Rider rider) {
        assert !riders.contains(rider)
            : "There should not be any existing references to a brand new Rider";

        riders.add(rider);

        assert riders.contains(rider)
            : "The new Rider should have been appended to riders";
    }

    /**
     * Drops the reference to the given Rider from the current Team.
     * 
     * @param rider Rider object to be removed from the current Team.
     */
    public void removeRider(Rider rider) {
        riders.remove(rider);
    }



    /**
     * Converts the current Team's details into a String.
     * 
     * @return String containing values for all fields of the Team.
     */
    public String toString() {
        String ridersString = Rider.toString(riders);
        return String.format("Team[id=%d, riders=%s, name=%s, description=%s,]", id, ridersString, name, description);
    }

    /**
     * Getter for the id field of the current Team.
     * 
     * @return ID of the current Team.
     */
    public int getId() {
        return id;
    }
}
