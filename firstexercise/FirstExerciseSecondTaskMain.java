package firstexercise;

/*
I have an array with texts
All the text in the array need its own thread
 */

import java.util.Arrays;

public class FirstExerciseSecondTaskMain {


    private static final String[] DEFAULT_STRINGS = ("apple pear banana walnut hyacinth  violet baker postman electrician ").split(" ");

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            start(DEFAULT_STRINGS);
        }else{
            start(args);
        }
    }

    public static void start(String[] strings){
        Arrays.stream(strings).forEach(FirstExerciseSecondTaskMain::start);
    }
    private static void print(String text){
        for (int i = 0; i < 100_000; i++) {
            System.out.println(String.format("%15s \t %05d", text,i));
        }
    }

    public static void start(String text){
        new Thread(()->print(text)).start();
    }
}

