package firstexercise;

/*
 * Method that works like print line
 * @param text and text one-one characters
 * after that endline
 */


import static java.lang.System.*;

public class FirstExerciseFifthTaskMain {

    public static void main(String[] args) {
        safePrintln("Hello World");
        safePrintln("Hello World");
        safePrintln("Hello World");
    }

    public static void safePrintln(String s) {
        synchronized (System.out) {
            System.out.println(s);
        }
    }
}
