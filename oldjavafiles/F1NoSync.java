package oldjavafiles;

import java.util.ArrayList;
import java.util.List;

public class F1NoSync {

    private static final int LIMIT = 1_000_000;
    private static final int NUMBER_OF_THREADS = 2;
    private static final List<Thread> threads = new ArrayList<>();
    private static final List<Integer> numbers = new ArrayList<>();

    public static void main(String[] args) {
        ValidationHelper.measureTime(F1NoSync::solve);
        ValidationHelper.validateAll(numbers, LIMIT);
    }

    private static void solve(){
        for(int i = 0; i < NUMBER_OF_THREADS; ++i){
            runThread(i);
        }
        threads.forEach(F1NoSync::waitThreadToFinish);
    }

    private static void runThread(int id){
        Thread t = new Thread( () -> {
            for (int i = id; i < LIMIT; i += NUMBER_OF_THREADS) {
                numbers.add(i + 1);
            }
        });
        threads.add(t);
        t.start();
    }

    private static void waitThreadToFinish(Thread t){
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
