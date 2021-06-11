package fifthexercise;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
 * Simple example

 */
enum TEAMS {
    BLUE_TEAM,
    GREEN_TEAM,
}

public class FirstTask05ExMain {
    private static String me = "Bird";
    private static final List<TEAMS> teams =
            Collections.unmodifiableList(Arrays.asList(TEAMS.values()));
    private static final int TEAMS_SIZE = teams.size();
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        new Thread(() -> {
            startMatch();
        }).start();
        waitForMatchToStart(me);
        celebrate(me);
    }

    private static void waitForMatchToStart(String name) {
        System.out.println(name + " is waiting for the match start.");
        synchronized (FirstTask05ExMain.class) {
            try {
                FirstTask05ExMain.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + " is watching the match");
    }

    private static void startMatch() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (FirstTask05ExMain.class) {
            FirstTask05ExMain.class.notify();
        }
        System.out.println(TEAMS.BLUE_TEAM + " and " + TEAMS.GREEN_TEAM + " match started!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        endMatch();

    }

    private static void endMatch() {
        System.out.println("Match ended " + winnerTeam() + " won!");
        synchronized (FirstTask05ExMain.class){
            FirstTask05ExMain.class.notify();
        }
    }

    private static void celebrate(String name) {
        synchronized (FirstTask05ExMain.class){
            try {
                FirstTask05ExMain.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + " is happy!");
    }

    public  static TEAMS winnerTeam(){
        return teams.get(RANDOM.nextInt(TEAMS_SIZE));
    }


}
