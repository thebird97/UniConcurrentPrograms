package eightSynchronizers.fx;

public class ExchangeRequest {

    private final Currency from;
    private final Currency to;
    private final int amount;
    private final boolean bonus;

    public ExchangeRequest(Currency from, Currency to, int amount, boolean bonus) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.bonus = bonus;
    }

    public Currency getFrom() {
        return from;
    }

    public Currency getTo() {
        return to;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isBonus(){
        return bonus;
    }

    @Override
    public String toString() {
        return "Request: "
                + amount + " from "
                + from.name() + " to "
                + to.name()
                + (bonus ? " with bonus" : " without bonus");
    }
}
