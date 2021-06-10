package fourthexercise.solution2;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class F4_5Parameterized {

    private static final int LIMIT = 1_000_000;
    private static final int NUMBER_OF_THREADS = 2;
    private static final boolean useSync = false;
    private static final boolean useSyncCollection = true;
    private static final boolean useOrdering = true;
    private static final List<Thread> threads = new ArrayList<>();
    private static final List<Integer> numbers =
            useSyncCollection ? Collections.synchronizedList(new ArrayList<>()) : new ArrayList<>();

    public static void main(String[] args) {
        ValidationHelper.measureTime(F4_5Parameterized::solve);
        ValidationHelper.validateAll(numbers, LIMIT);
    }

    private static void solve(){
        for(int i = 0; i < NUMBER_OF_THREADS; ++i){
            runThread(i);
        }
        threads.forEach(F4_5Parameterized::waitThreadToFinish);
    }

    private static void runThread(int id){
        Thread t = new Thread( () -> {
            for(int i = id; i < LIMIT; i += NUMBER_OF_THREADS){
                if(useOrdering)
                    waitForPreviousNumber(i);
                if(useSync) {
                    synchronized (numbers) {
                        numbers.add(i + 1);
                    }
                } else {
                    numbers.add(i + 1);
                }
            }
        });
        threads.add(t);
        t.start();
    }

    private static void waitForPreviousNumber(int id) {
        while (true) {
            if(useSyncCollection) {
                if (numbers.size() == id) break;
            } else {
                int size;
                synchronized (numbers) {
                    size = numbers.size();
                }
                if (size == id) break;
            }
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
