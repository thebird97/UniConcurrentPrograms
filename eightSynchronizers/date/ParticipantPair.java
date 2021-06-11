package eightSynchronizers.date;

public class ParticipantPair {

    private final Participant one;
    private final Participant other;

    public ParticipantPair(Participant one, Participant other) {
        this.one = one;
        this.other = other;
    }

    public Participant getOne() {
        return one;
    }

    public Participant getOther() {
        return other;
    }
}
