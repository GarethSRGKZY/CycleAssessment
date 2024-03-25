package cycling;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable {
    // Static Attributes
    private static int nextId = 0;

    // Static Methods
    public static Team findTeamById(ArrayList<Team> teams, int teamId) throws IDNotRecognisedException {
        for (Team team : teams) {
            if (team.getId() == teamId) {
                return team;
            }
        }

        throw new IDNotRecognisedException(String.format("Team id %d not found", teamId));
    }

    public static Team findTeamByName(ArrayList<Team> teams, String teamName) throws NameNotRecognisedException {
        for (Team team : teams) {
            if (team.getName().equals(teamName)) {
                return team;
            }
        }

        throw new NameNotRecognisedException(String.format("Team name %s not found", teamName));
    }

    public static void eraseTeams(ArrayList<Team> teams) {
        teams.clear();
        nextId = 0;
    }

    public static void loadTeams(ArrayList<Team> teams) {
        int[] teamIds = toIds(teams);

        for (int teamId : teamIds) {
            if (teamId >= nextId) {
                nextId = teamId + 1;
            }
        }
    }

    public static int[] toIds(ArrayList<Team> teams) {
        int size = teams.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = teams.get(i).getId();
        }

        return result;
    }

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

    public String getName() {
        return name;
    }

    public ArrayList<Rider> getRiders() {
        return riders;
    }

    public void addRider(Rider rider) {
        assert !riders.contains(rider)
            : "There should not be any existing references to a brand new Rider";

        riders.add(rider);

        assert riders.contains(rider)
            : "The new Rider should have been appended to riders";
    }

    public void removeRider(Rider rider) {
        riders.remove(rider);
    }



    public String toString() {
        String ridersString = Rider.toString(riders);
        return String.format("Team[id=%d, riders=%s, name=%s, description=%s,]", id, ridersString, name, description);
    }

    public int getId() {
        return id;
    }
}
