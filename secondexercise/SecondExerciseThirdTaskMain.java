package secondexercise;

/*
 * Thread priority
 *
 */

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SecondExerciseThirdTaskMain {


    private static final String[] DEFAULT_STRINGS = ("apple pear banana walnut hyacinth  violet baker postman electrician ").split(" ");
    private static final AtomicLong ALONG = new AtomicLong(0L);
    private static final Consumer<String> println = System.out::println;

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            startAndWaitAll(DEFAULT_STRINGS);
        } else {
            startAndWaitAll(DEFAULT_STRINGS);
        }
    }

    private static void startAndWaitAll(String[] strings) {
        List<Thread> threads = Arrays.stream(strings).map(SecondExerciseThirdTaskMain::startAndWaitAll).collect(Collectors.toList());
        threads.forEach(t->{
            try {
                t.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });
    }

    private static Thread startAndWaitAll(String text){
        Thread t = new Thread(()->print(text));
        t.setPriority((int)ALONG.incrementAndGet() % Thread.MAX_PRIORITY + Thread.MIN_PRIORITY);
        t.start();
        return t;
    }

    public static void start(String[] strings){
        Arrays.stream(strings).forEach(firstexercise.FirstExerciseSecondTaskMain::start);
    }
    private static void print(String text){
        for (int i = 0; i < 100_000; i++) {
            System.out.println(String.format("%2d %15s \t %05d",Thread.currentThread().getPriority() ,text,i));
        }
    }


}