package sevenLocks.solution;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static final int LIMIT = 20;
    private static BlockingQueue<Integer> bQueue = new LinkedBlockingDeque<>(2);
    private static ExecutorService executor = Executors.newFixedThreadPool(LIMIT/2);
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        executor.submit(Main::bQueueExample);
        for(int i = 0; i < LIMIT; ++i){
            int num = i;
            executor.submit(() -> producer(num));
        }

        synchronized (Main.class){
            try {
                Main.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < LIMIT; ++i){
            int num = i;
            executor.submit(() -> lockExample(num));
        }

        executor.shutdown();
    }

    private static void producer(Integer num){
        boolean success = false;
        while(!success){
            success = bQueue.offer(num);
            if(!success) {
                System.out.println("Couldnt add " + num);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void bQueueExample(){
        int counter = 0;
        while(counter != LIMIT) {
            try {
                Integer result = bQueue.poll(1000, TimeUnit.MILLISECONDS);
                if (result == null)
                    continue;
                counter++;
                System.out.println(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (Main.class){
            Main.class.notify();
        }
    }

    private static void lockExample(int i){
        try {
            lock.lock();
            System.out.println("using lock: " + i);
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

