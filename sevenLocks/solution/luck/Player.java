package sevenLocks.solution.luck;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;
    private final List<Ticket> tickets;

    public Player(String name) {
        this.name = name;
        tickets = new ArrayList<>();
    }

    public void buyTicket(Ticket ticket){
        tickets.add(ticket);
        System.out.println(name + " bought a ticket");
    }

    public boolean hasWinnerTicket(){
        return tickets.stream().filter(t -> t.isWinner()).count() > 0;
    }

    public String getName(){
        return name;
    }
}
