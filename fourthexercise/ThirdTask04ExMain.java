package fourthexercise;

import java.util.ArrayList;
import java.util.List;

/*
 * Put elements to a list with two threads,
 * The first thread contains odd numbers between 1 and 1 000 000
 * The second thread contains even numbers between 1 and 1 000 000
 * Initially don't synchronize the onload.
 *
 * With 4-5 Task
 * Each running thread should declared one time, use a assistant method.
 * Use Thread auxiliary function(@param which thread need to work)  -> make the threads and starts them
 * <p>
 *
 *  Hungarian Description
 * Egy listába tegyél be 2 szálon úgy értékeket, hogy az egyik 1 és 1 000 000 között páratlan
 * a másik 1 - 1 000 000 páros értékeket tartalmaz
 * A listaba az elemek berakását kezdetben ne szinkronizáld
 *  4 - 5
 * old meg hogy amit a szálak futtatnak csak egyszer legyen leírva forráskódba egy segéd metóduban és a szálak készítésekor ezt hívd meg
 * use thread segédfüggvény  ami megkapja melyik szállal kell dolgoznia  (elkészíti a szálakat, elindítja, bevárja hogy lefusson)
 */
import java.util.ArrayList;
import java.util.List;

public class ThirdTask04ExMain {

    private static final int LIMIT = 1_000_000;
    private static final int NUMBER_OF_THREADS = 2;
    private static final List<Thread> threads = new ArrayList<>();
    private static final List<Integer> numbers = new ArrayList<>();

    public static void main(String[] args) {
        ValidationHelper.measureTime(ThirdTask04ExMain::solve);
        ValidationHelper.validateAll(numbers, LIMIT);
    }

    private static void solve(){
        for(int i = 0; i < NUMBER_OF_THREADS; ++i){
            runThread(i);
        }
        threads.forEach(ThirdTask04ExMain::waitThreadToFinish);
    }

    private static void runThread(int id){
        Thread t = new Thread( () -> {
            for(int i = id; i < LIMIT; i += NUMBER_OF_THREADS){
                waitForPreviousNumber(i);
                numbers.add(i+1);
            }
        });
        threads.add(t);
        t.start();
    }

    private static void waitForPreviousNumber(int id) {
        while (true) {
            int size;
            synchronized (numbers) {
                size = numbers.size();
            }
            if (size == id)    break;
        }
    }

    private static void waitThreadToFinish(Thread t){
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
