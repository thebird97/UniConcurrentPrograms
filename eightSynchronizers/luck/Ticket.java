package eightSynchronizers.luck;

public class Ticket {

    private static int idGenerator = 0;
    private final int id;
    private final boolean winner;

    public Ticket( int winnerId) {
        this.id = idGenerator++;
        this.winner = winnerId == this.id;
    }

    public int getId(){
        return this.id;
    }

    public boolean isWinner(){
        return this.winner;
    }
}
