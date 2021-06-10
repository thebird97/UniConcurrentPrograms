package fourthexercise.solution2;


import java.util.ArrayList;
import java.util.List;

public class F1DoSync {

    private static final int LIMIT = 1_000_000;
    private static final int NUMBER_OF_THREADS = 2;
    private static final List<Thread> threads = new ArrayList<>();
    private static final List<Integer> numbers = new ArrayList<>();

    public static void main(String[] args) {
        ValidationHelper.measureTime(F1DoSync::solve);
        ValidationHelper.validateAll(numbers, LIMIT);
    }

    private static void solve(){
        for(int i = 0; i < NUMBER_OF_THREADS; ++i){
            runThread(i);
        }
        threads.forEach(F1DoSync::waitThreadToFinish);
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
