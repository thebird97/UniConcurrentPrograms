package firstexercise;

/* Exercise:
 *Make two threads
 * The threads write out: hello 1, hello 2 ... hello 100 000 and world 1 .... 100 000
 * Check the outputs
 * First not concurrent
 */


class FirstExerciseFirstTaskMain {
    public static void main(String[] args) {
        start("hello");
        start("wold");
        //third thread ->
        start("other");

    }

    private static void print(String text) {
        for (int i = 0; i < 100_000; i++) {
            System.out.printf("%s %05d%n", text, i);
        }
    }
    /*
    lambda expression
    not concurrent
    private static void start(String text){ new Thread(()->print(text)).run();}
    */

    private static void start(String text) {
        new Thread(() -> print(text)).start();
    }
}