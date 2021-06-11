package fifthexercise.solution;

public class Main {

    private static String me = "Norbi";

    public static void main(String[] args) {
	    new Thread(() -> {
	        startMatch();
        }).start();
	    waitForMatchToStart(me);
	    celebrate(me);
    }

    private static void waitForMatchToStart(String someone){
        System.out.println(someone + " is waiting for the match to start");
        synchronized (Main.class) {
            try {
                Main.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(someone + " is watching the match");
    }

    private static void startMatch(){

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (Main.class) {
            Main.class.notify();
        }
        System.out.println("Arsenal vs Tottenham started");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        endMatch();
    }

    private static void endMatch(){
        System.out.println("Match ended, Arsenal won");
        synchronized (Main.class) {
            Main.class.notify();
        }
    }

    private static void celebrate(String someone){
        synchronized (Main.class) {
            try {
                Main.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(someone + " is happy because Arsenal won");

    }

}
