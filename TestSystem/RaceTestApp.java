import cycling.Race;

public class RaceTestApp {
    public static void main(String[] args) {
        Race a = new Race("name", "description");

        a.addStage(123);
        a.addStage(456);

        System.out.println(a);

        a.removeStage(123);

        System.out.println(a);



        Race b = new Race("b", "b");

        System.out.println(b);



        for (int id : Race.getRaceIds()) {
            System.out.println(id);
        }

        Race a_test = Race.getRaceById(0);

        System.out.println(a == a_test);



        // This should remove a
        Race.removeRaceById(0);

        for (int id : Race.getRaceIds()) {
            System.out.println(id);
        }
    }
}
