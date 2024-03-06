import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cycling.CyclingPortal;
import cycling.CyclingPortalImpl;
import cycling.IDNotRecognisedException;
import cycling.IllegalNameException;
import cycling.InvalidNameException;
import cycling.NameNotRecognisedException;

public class RaceTest {
    public static void main(String[] args) {
        RaceTest test = new RaceTest();
        test.test();
    }

    public void test() {
        System.out.println(testGetRaceIds() + 
        " - testGetRaceIds");
        System.out.println(testCreateRace() + 
        " - testCreateRace");
        System.out.println(testCreateRaceNameClash() + 
        " - testCreateRaceNameClash");
        System.out.println(testCreateRaceNameNull() + 
        " - testCreateRaceNameNull");
        System.out.println(testCreateRaceNameEmpty() + 
        " - testCreateRaceNameEmpty");
        System.out.println(testCreateRaceName30Chars() + 
        " - testCreateRaceName30Chars");
        System.out.println(testCreateRaceName31Chars() + 
        " - testCreateRaceName31Chars");
        System.out.println(testCreateRaceNameWhiteSpace() + 
        " - testCreateRaceNameWhiteSpace");
        System.out.println(testViewRaceDetails() + 
        " - testViewRaceDetails");
        System.out.println(testRemoveRaceById() + 
        " - testRemoveRaceById");
        System.out.println(testRemoveRaceByName() + 
        " - testRemoveRaceByName");
    }

    public boolean testGetRaceIds() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testCreateRace() {
        CyclingPortal portal1 = new CyclingPortalImpl();
        CyclingPortal portal2 = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal1.getRaceIds().length == 0);
        assert (condition)
            : "portal1 should not contain any Races";
        if (!condition) {
            return false;
        }

        int raceIdA;
        try {
            raceIdA = portal1.createRace("A", "Race A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }

        condition = (portal1.getRaceIds()[0] == raceIdA && portal1.getRaceIds().length == 1);
        assert (condition)
            : "portal1 should contain one Race with ID consistent with ID returned from createRace";
        if (!condition) {
            return false;
        }
        
        condition = (portal2.getRaceIds().length == 0);
        assert (condition)
            : "creating a Race in portal1 should not affect the number of races in portal2";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testCreateRaceNameClash() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        try {
            portal.createRace("A", "Race A");
            portal.createRace("A", "Race A");
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

        condition = (portal.getRaceIds().length == 1);
        assert (condition)
            : "the second Race should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testCreateRaceNameNull() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        try {
            portal.createRace(null, "Race A");
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

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "the Race should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testCreateRaceNameEmpty() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        try {
            portal.createRace("", "Race A");
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

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "the Race should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testCreateRaceName30Chars() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        try {
            portal.createRace("123456789_123456789_123456789_", "Race A");
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

        return true;
    }

    public boolean testCreateRaceName31Chars() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        try {
            portal.createRace("123456789_123456789_123456789_1", "Race A");
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

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "the Race should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testCreateRaceNameWhiteSpace() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        try {
            portal.createRace("Race A", "Race A");
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

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "the Race should not have been created";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testViewRaceDetails() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        int raceIdA;
        try {
            raceIdA = portal.createRace("A", "Race A");
        } catch (IllegalNameException | InvalidNameException e) {
            e.printStackTrace();
            return false;
        }
        
        String raceDetails;
        try {
            raceDetails = portal.viewRaceDetails(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        Pattern pattern = Pattern.compile("Race\\[id=[0-9]+, stages=\\{[A-Za-z0-9=,.\\-:\\{\\}\\[\\]\\s]*\\}, name=[A-Za-z0-9]+, description=[A-Za-z0-9\\s]*\\]$");
        Matcher matcher = pattern.matcher(raceDetails);

        condition = matcher.matches();
        assert (condition)
            : "Race details must match the regex pattern";
        if (!condition) {
            System.out.println("NO MATCH");
            return false;
        }
        
        return true;
    }

    public boolean testRemoveRaceById() {
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
        
        try {
            portal.removeRaceById(raceIdA);
        } catch (IDNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "the Race should have been removed";
        if (!condition) {
            return false;
        }

        return true;
    }

    public boolean testRemoveRaceByName() {
        CyclingPortal portal = new CyclingPortalImpl();
        boolean condition = false;

        String raceNameA = "A";

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "portal should not contain any Races";
        if (!condition) {
            return false;
        }
        
        try {
            portal.createRace("A", "Race A");
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
        
        try {
            portal.removeRaceByName(raceNameA);
        } catch (NameNotRecognisedException e) {
            e.printStackTrace();
            return false;
        }

        condition = (portal.getRaceIds().length == 0);
        assert (condition)
            : "the Race should have been removed";
        if (!condition) {
            return false;
        }

        return true;
    }
}