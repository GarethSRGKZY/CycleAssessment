package cycling;

import java.util.ArrayList;

public class Team {
    // Static Attributes
    private int nextId = 0;

    // Static Methods
    public static Team findTeamById(ArrayList<Team> teamInstances, int teamId) throws IDNotRecognisedException {
        for (Team team : teamInstances) {
            if (team.getId() == teamId) {
                return team;
            }
        }

        throw new IDNotRecognisedException(String.format("Team id %d not found", teamId));
    }

    // TODO loadTeams()
    // TODO saveTeams()

    public static int[] toIds(ArrayList<Team> teamInstances) {
        int size = teamInstances.size();

        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = teamInstances.get(i).getId();
        }

        return result;
    }

    public static String toString(ArrayList<Team> teamInstances) {
        String[] teamStrings = new String[teamInstances.size()];

        for (int i = 0; i < teamInstances.size(); ++i) {
            teamStrings[i] = teamInstances.get(i).toString();
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

        this.id = nextId++;
        this.riders = new ArrayList<>();

        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Rider> getRiders() {
        return this.riders;
    }

    public void addRider(Rider rider) {
        assert !this.riders.contains(rider)
            : "There should not be any existing references to a brand new Rider";

        this.riders.add(rider);

        assert this.riders.contains(rider)
            : "The new Rider should have been appended to this.riders";
    }

    public void removeRider(Rider rider) {
        this.riders.remove(rider);
    }



    public String toString() {
        String riders = Rider.toString(this.riders);
        return String.format("Team[id=%d, riders=%s, name=%s, description=%s,]", this.id, riders, this.name, this.description);
    }

    public int getId() {
        return this.id;
    }
}
