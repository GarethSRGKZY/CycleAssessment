import cycling.CyclingPortal;
import cycling.CyclingPortalImpl;
import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.InvalidNameException;

public class TeamTest {
    public static void main(String[] args) {
        TeamTest test = new TeamTest();
        test.test();
    }

    public void test() {
        System.out.println(testCreateTeam() + 
        " - testCreateTeam");
        System.out.println(testCreateTeamNameClash() + 
        " - testCreateTeamNameClash");
        System.out.println(testCreateTeamNameNull() + 
        " - testCreateTeamNameNull");
        System.out.println(testCreateTeamNameEmpty() + 
        " - testCreateTeamNameEmpty");
        System.out.println(testCreateTeamName30Chars() + 
        " - testCreateTeamName30Chars");
        System.out.println(testCreateTeamName31Chars() + 
        " - testCreateTeamName31Chars");
        System.out.println(testCreateTeamNameWhiteSpace() + 
        " - testCreateTeamNameWhiteSpace");
        System.out.println(testRemoveTeam() + 
        " - testRemoveTeam");
        System.out.println(testGetTeams() + 
        " - testGetTeams");
    }

    public boolean testCreateTeam() {
        CyclingPortal portal1 = new CyclingPortalImpl();
        CyclingPortal portal2 = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal1.getTeams().length == 0);
        assert (condition)
            : "portal1 should not contain any Teams";
        if (!condition) {
            return false;
        }

        int teamIdA;
        try {
            teamIdA = portal1.createTeam("A", "Team A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }

        condition = (portal1.getTeams()[0] == teamIdA && portal1.getTeams().length == 1);
        assert (condition)
            : "portal1 should contain one Team with ID consistent with ID returned from createTeam";
        if (!condition) {
            return false;
        }
        
        condition = (portal2.getTeams().length == 0);
        assert (condition)
            : "creating a Team in portal1 should not affect the number of teams in portal2";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testCreateTeamNameClash() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getTeams().length == 0);
        assert (condition)
            : "portal should not contain any Teams";
        if (!condition) {
            return false;
        }
        
        try {
            portal.createTeam("A", "Team A");
            portal.createTeam("A", "Team A");
            assert false
                : "expected IllegalNameException but got none";
            return false;
        } catch (InvalidNameException e) {
            e.printStackTrace();
            assert false
                : "expected IllegalNameException but got InvalidNameException";
            return false;
        } catch (IllegalNameException e) {
            // Do nothing
        }

        condition = (portal.getTeams().length == 1);
        assert (condition)
            : "the second Team should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testCreateTeamNameNull() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getTeams().length == 0);
        assert (condition)
            : "portal should not contain any Teams";
        if (!condition) {
            return false;
        }
        
        try {
            portal.createTeam(null, "Team A");
            assert false
                : "expected InvalidNameException but got none";
            return false;
        } catch (IllegalNameException e) {
            e.printStackTrace();
            assert false
                : "expected InvalidNameException but got IllegalNameException";
            return false;
        } catch (InvalidNameException e) {
            // Do nothing
        }

        condition = (portal.getTeams().length == 0);
        assert (condition)
            : "the Team should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testCreateTeamNameEmpty() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getTeams().length == 0);
        assert (condition)
            : "portal should not contain any Teams";
        if (!condition) {
            return false;
        }
        
        try {
            portal.createTeam(null, "Team A");
            assert false
                : "expected InvalidNameException but got none";
            return false;
        } catch (IllegalNameException e) {
            e.printStackTrace();
            assert false
                : "expected InvalidNameException but got IllegalNameException";
            return false;
        } catch (InvalidNameException e) {
            // Do nothing
        }

        condition = (portal.getTeams().length == 0);
        assert (condition)
            : "the Team should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testCreateTeamName30Chars() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getTeams().length == 0);
        assert (condition)
            : "portal should not contain any Teams";
        if (!condition) {
            return false;
        }
        
        try {
            portal.createTeam("123456789_123456789_123456789_", "Team A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }

        condition = (portal.getTeams().length == 1);
        assert (condition)
            : "the Team should have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testCreateTeamName31Chars() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getTeams().length == 0);
        assert (condition)
            : "portal should not contain any Teams";
        if (!condition) {
            return false;
        }
        
        try {
            portal.createTeam("123456789_123456789_123456789_1", "Team A");
            assert false
                : "expected InvalidNameException but got none";
            return false;
        } catch (IllegalNameException e) {
            e.printStackTrace();
            assert false
                : "expected InvalidNameException but got IllegalNameException";
            return false;
        } catch (InvalidNameException e) {
            // Do nothing
        }

        condition = (portal.getTeams().length == 0);
        assert (condition)
            : "the Team should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testCreateTeamNameWhiteSpace() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getTeams().length == 0);
        assert (condition)
            : "portal should not contain any Teams";
        if (!condition) {
            return false;
        }
        
        try {
            portal.createTeam("Team A", "Team A");
            assert false
                : "expected InvalidNameException but got none";
            return false;
        } catch (IllegalNameException e) {
            e.printStackTrace();
            assert false
                : "expected InvalidNameException but got IllegalNameException";
            return false;
        } catch (InvalidNameException e) {
            // Do nothing
        }

        condition = (portal.getTeams().length == 0);
        assert (condition)
            : "the Team should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testRemoveTeam() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getTeams().length == 0);
        assert (condition)
            : "portal should not contain any Teams";
        if (!condition) {
            return false;
        }
        
        int teamIdA;
        try {
            teamIdA = portal.createTeam("A", "Team A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }

        condition = (portal.getTeams().length == 1);
        assert (condition)
            : "the Team should have been created";
        if (!condition) {
            return false;
        }
        
        try {
            portal.removeTeam(teamIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (portal.getTeams().length == 0);
        assert (condition)
            : "the Team should have been removed";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testGetTeams() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getTeams().length == 0);
        assert (condition)
            : "portal should not contain any Teams";
        if (!condition) {
            return false;
        }

        return true;
    }
}