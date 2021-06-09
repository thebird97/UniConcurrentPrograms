package secondexercise;

/*
* Make threads with different methods

 */

public class SecondExerciseFourthTaskMain {
    public static void main(String[] args) {
        Thread runnable = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("This thread with noname runnable.");
                    }
                }
        );
        Thread threadwithlambda = new Thread(()->{
            System.out.println("Thread with lambda parameter.");
        });

        Thread threadwithreference = new Thread(SecondExerciseFourthTaskMain::referralMethod);
        Thread threadWithRunnableCopy = new Thread(new MyRunnable());
        Thread threadDescend = new MyThread();

        runnable.start();
        threadwithlambda.start();
        threadwithreference.start();
        threadWithRunnableCopy.start();
        threadDescend.start();

    }

    public static void referralMethod(){
        System.out.println("This thread made with referral method.");
    }

    private static class MyRunnable implements Runnable{

        @Override
        public void run() {
            System.out.println("This thread gets a runnable copy.");
        }
    }

    private static class MyThread extends Thread{

        @Override
        public void run() {
            System.out.println("This thread is a descendant thread.");
        }
    }
}
