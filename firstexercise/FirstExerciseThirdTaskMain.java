package firstexercise;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/*
 * Write the output to a file with PrintWriter
 * Close the file after the write!
 */
public class FirstExerciseThirdTaskMain {
    private static final String[] DEFAULT_STRINGS = ("apple pear banana walnut hyacinth  violet baker postman electrician ").split(" ");
    private static Consumer<String> println = System.out::println;
    //same as private static Consumer<String> printlnEx = x -> System.out.println(x);

    public static void main(String[] args) {
        try (PrintWriter pw = new PrintWriter(new File("output.txt"))) {
            println = pw::println;
            if (args == null || args.length == 0) {
                startAndWaitAll(DEFAULT_STRINGS);
            } else {
                startAndWaitAll(DEFAULT_STRINGS);
            }
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void startAndWaitAll(String[] strings) {
        List<Thread> threads = Arrays.stream(strings).map(FirstExerciseThirdTaskMain::startAndWaitAll).collect(Collectors.toList());
        threads.forEach(t->{
            try {
                t.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });
    }

    private static void print(String text){
        for (int i = 0; i < 100_000; i++) {
            FirstExerciseThirdTaskMain.println.accept(String.format("%15s \t %05d",text, i));
        }
    }

    private static Thread startAndWaitAll(String text){
        Thread t = new Thread(() -> print(text));
        t.start();
        return t;
    }
}