package sixthExecutors;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
 *

 * Hungarian description
 *
 */
public class MainExecutors {
    private static final int MAX_WAIT_MSECS = 1000;
    private static final int MAX_WAIT_SECS = 10;
    private static final int NUMBER_OF_PATIENTS = 100;
    private final static AtomicInteger vaccinesCreated = new AtomicInteger(0);
    private final static AtomicInteger vaccinesUsed = new AtomicInteger(0);
    private static int vaccines = 0;
    private static Object vaccineLock = new Object();
    private static List<Thread> threads = new ArrayList<>();

    public static void main(String[] args) {
        useManualThreads();
        //useSingleThreadExecutor(); very long running time
        useMultiThreadExecutor();
        useScheduledExecutor();
    }
    private static void useManualThreads() {
        System.out.println("Starting threads manually...");

        for (int i = 0; i < NUMBER_OF_PATIENTS; i++) {
            Thread thread = i % 2 == 0 ? new Thread(MainExecutors::producer) : new Thread(MainExecutors::consumer);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        printAndResetResult();
    }

    private static void producer() {
        sleepSomeTime();
        synchronized (vaccineLock){
            vaccines++;
        }
        vaccinesCreated.incrementAndGet();
    }

    private static void sleepSomeTime() {
        int msecs = ThreadLocalRandom.current().nextInt(MAX_WAIT_MSECS);
        sleepForMses(msecs);
    }

    private static void sleepForMses(int msecs) {
        try {
            TimeUnit.MICROSECONDS.sleep(msecs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void consumer() {
        boolean  gotVaccine = false;
        while (!gotVaccine){
            synchronized (vaccineLock){
                if(vaccines>0){
                    --vaccines;
                    vaccinesUsed.incrementAndGet();
                    gotVaccine = true;
                }
            }
        }
    }


    private static void useSingleThreadExecutor() {
    }

    private static void useScheduledExecutor() {
    }

    private static void useMultiThreadExecutor() {
    }


    private static void printAndResetResult() {
        System.out.println("Remaining vaccines: " + vaccines);
        System.out.println("Produced vaccines: " + vaccinesCreated.get());
        System.out.println("Used vaccines: " + vaccinesUsed.get());

        vaccines = 0;
        vaccinesUsed.set(0);
        vaccinesCreated.set(0);
    }


}
