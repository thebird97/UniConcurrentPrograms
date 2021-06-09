package thirdexercise;

/*
* Make two threads, that make 10-10 more threads
* That write out hello1... etc. and world1... etc.

 */
public class FirstTask03ExMain {
    private static int moreThread = 10;
    public static void main(String[] args) {
      new Thread(() -> threadMakerAndStarter("hello")).start();
      new Thread(() -> threadMakerAndStarter("world")).start();
    }

    public static void threadMakerAndStarter(String word){
        for (int i = 0; i < moreThread; i++) {
            final int finalizedI = i;
            new Thread(()->{
                for (int j = 0; j < 100_000; j++) {
                    System.out.println(String.format("%02d \t %04d \t %s",finalizedI,j,word));
                }
            }).start();
        }
    }
}
