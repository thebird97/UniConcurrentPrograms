package secondexercise;
/*
* Pause threads check variables and rewrite variables and group the threads using IDE
* Using VirtualVM
 */

public class SecondExerciseSecondTaskMain {
    public static void main(String[] args) {
        ThreadGroup group1 = new ThreadGroup("first group");
        ThreadGroup group2 = new ThreadGroup(group1,"second group");

        //unnamed threads
        new Thread(SecondExerciseSecondTaskMain::threadMessenger).start();
        new Thread(SecondExerciseSecondTaskMain::threadMessenger).start();

        //thread without group
        new Thread(SecondExerciseSecondTaskMain::threadMessenger, "Thread without group");

        //Threads with group
        new Thread(group1, SecondExerciseSecondTaskMain::threadMessenger, "First group first thread");
        new Thread(group1, SecondExerciseSecondTaskMain::threadMessenger, "First group second thread");

        new Thread(group2, SecondExerciseSecondTaskMain::threadMessenger, "Second group first thread");
        new Thread(group2, SecondExerciseSecondTaskMain::threadMessenger, "Second group second thread");

        new Thread(SecondExerciseSecondTaskMain::emptyLine).start();


    }

    private static void threadMessenger() {
        printEverySecond("This is a thread: " + Thread.currentThread().getName() +
                " In this group: " + Thread.currentThread().getThreadGroup().getName() +
                " the parent: " + Thread.currentThread().getThreadGroup().getParent());
    }


    private static void emptyLine() {
        printEverySecond("");
    }

    private static void printEverySecond(String message) {
        try {
            for (int i = 0; i < 100; i++) {
                System.out.println(message);
                Thread.sleep(1000);
            }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }



