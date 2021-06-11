package sevenLocks.solution.running;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Competition {

    private static final int RUNNER_NUMBER = 4;
    private static final int RACE_NUMBER = 5;
    private static final ExecutorService executor = Executors.newFixedThreadPool(RUNNER_NUMBER);
    private static final Phaser phaser = new Phaser(RUNNER_NUMBER);
    private static final CountDownLatch latch = new CountDownLatch(RUNNER_NUMBER);
    private static final List<Runner> runners = new ArrayList<>();
    private static final Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {

        runners.add(new Runner("Stan"));
        runners.add(new Runner("Kyle"));
        runners.add(new Runner("Cartman"));
        runners.add(new Runner("Kenny"));

        runners.forEach(r -> executor.submit(() -> runnerAction(r)));

        try {
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor.shutdownNow();
        runners.forEach(r -> System.out.println(r.getName() + " won " + r.getWins() + " races"));

    }

    private static void runnerAction(Runner runner){
        phaser.arriveAndAwaitAdvance();

        int currentRace = 1;
        while(currentRace <= RACE_NUMBER){
            System.out.println(runner.getName() + " is starting race " + currentRace);
            runner.run();

            try {
                semaphore.acquire();
                if(phaser.getArrivedParties() == 0){
                    runner.incrementWins();
                }
                phaser.arrive();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
            phaser.awaitAdvance(currentRace );

            System.out.println(runner.getName() + " finished the race");
            ++currentRace;
        }
        latch.countDown();

    }
}
