package sevenLocks.solution.running;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Runner {

    private static final Random rand = new Random();
    private final String name;
    private int wins;

    public Runner(String name) {
        this.name = name;
        wins = 0;
    }

    public void run(){
        sleepForMsec(2000 + rand.nextInt(2000));
    }

    public void incrementWins(){
        wins++;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    private void sleepForMsec(int sleepTime) {
        try {
            TimeUnit.MILLISECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            // dont care
        }
    }
}
