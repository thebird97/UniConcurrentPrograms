package eightSynchronizers.luck;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

public class Simulator {

    private static final Random random = new Random();
    private static final int TICKET_LIMIT = 20;
    private static final int PLAYER_NUMBER = 3;
    private static final Queue<Ticket> tickets = new ConcurrentLinkedQueue<>();
    private static final CountDownLatch latch = new CountDownLatch(TICKET_LIMIT);
    private static final ExecutorService executor = Executors.newFixedThreadPool(PLAYER_NUMBER);
    private static final List<Player> players = new ArrayList<>();

    public static void main(String[] args) {
        int winnerId = random.nextInt(TICKET_LIMIT) + 1;

        for(int i = 0; i < TICKET_LIMIT; i++){
            tickets.add(new Ticket(winnerId));
        }

        players.add(new Player("Rick"));
        players.add(new Player("Morty"));
        players.add(new Player("Summer"));

        players.forEach(p -> executor.submit(() -> playerAction(p)));

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        players.stream()
                .filter(p -> p.hasWinnerTicket())
                .findFirst()
                    .ifPresent(p -> System.out.println("Winner is: " + p.getName()));
        executor.shutdownNow();
    }

    public static void playerAction(Player player){
        while(!tickets.isEmpty()){
            player.buyTicket(tickets.poll());
            latch.countDown();
            sleepForMsec(500 + random.nextInt(500));
        }
    }

    private static void sleepForMsec(int sleepTime) {
        try {
            TimeUnit.MILLISECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            // dont care
        }
    }
}
