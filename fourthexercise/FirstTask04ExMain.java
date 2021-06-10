package fourthexercise;
/*
 * Write out the active threads in every secs
 * Thread.concurrent.getthread().activatecount
 * use sleep
 *
 */

public class FirstTask04ExMain {
    private static final String[] WORDS = "apple pear peach plum nut cherry".split(" ");

    public static void main(String[] args) {
        ThreadGroup myGroup = new ThreadGroup("1st group");
        for(String word : WORDS){
            new Thread(myGroup, () -> {
                for (int i = 0; i < 100; i++) {
                    System.out.println(String.format("%05d \t %15s", i, word));
                }
            },word).start();
        }

        new Thread(myGroup, ()-> {
            try {
                boolean shouldStop = false;
                while (!shouldStop){
                    Thread.sleep(1000);
                    if (myGroup.activeCount() == 1) {
                        System.out.println("This is the last active thread. Application stop.");
                        shouldStop = true;
                    }else {
                        System.out.println("Waiting for threads to stop.");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        },"waiter thread").start();
    }
}
