package fourthexercise;
/*
 * Write out the active threads in every secs
 * Thread.concurrent.getthread().activatecount
 * Use Timer
 *
 */

import java.util.Timer;
import java.util.TimerTask;


public class SecondTask04ExMain {
    private static final String[] WORDS = "apple pear peach plum nut cherry".split(" ");

    public static void main(String[] args) {
        ThreadGroup myGroup = new ThreadGroup("1st group");
        for (String word : WORDS) {
            new Thread(myGroup, () -> {
                for (int i = 0; i < 100; i++) {
                    System.out.println(String.format("%05d \t %15s", i, word));
                }
            }, word).start();
        }


        Timer timer = new Timer("waiter timer");

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    if (myGroup.activeCount() == 0) {
                        System.out.println("This is the last active thread. Application stop");
                        cancel(); //timer task canceling
                    } else {
                        System.out.println("Waiting for other threads to stop");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }, 0L, 1000L);
        //Delay at first run
        //Period the time gaps

    }
}
