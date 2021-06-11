package sevenLocks.solution.date;

import java.util.*;
import java.util.List;
import java.util.concurrent.*;

public class DateSimulator {

    private static final int DATE_LIMIT = 10;
    private static final int PARTICIPANT_LIMIT = 4;
    private static final Random rand = new Random();
    private static final Queue<String> participantNames = new LinkedList<>();
    private static final List<Participant> participants = new ArrayList<>();
    private static final Exchanger<Attribute> exchanger = new Exchanger<>();
    private static final ExecutorService executor = Executors.newFixedThreadPool(PARTICIPANT_LIMIT);
    private static final CountDownLatch latch = new CountDownLatch(PARTICIPANT_LIMIT);
    private static final List<ParticipantPair> pairs = new ArrayList<>();

    public static void main(String[] args) {
        participantNames.add("Ted Bundy");
        participantNames.add("Heisenberg");
        participantNames.add("Bellatrix Lestrange");
        participantNames.add("Harley Quinn");

        setupParticipants();

        participants.forEach(p -> executor.submit(() -> participantAction(p)));

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print("Making matches and evaluating pairs...");
        makeMatches();
        evaluatePairs();

        executor.shutdownNow();
    }

    private static void participantAction(Participant participant){
        int dateCounter = 0;
        try {
            while (dateCounter < DATE_LIMIT) {
                Attribute own = participant.getRandomAttribute();
                Attribute other = exchanger.exchange(own);
                participant.addToPreferences(other);
                dateCounter++;
                System.out.println(participant.getName() + " dates: " + dateCounter);
                sleepForMsec(1000);
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
        System.out.println(participant.getName() + " finished dating");
    }

    private static void setupParticipants(){
        while(!participantNames.isEmpty()){
            Participant par = new Participant(participantNames.poll());
            int numberOfAttributes = rand.nextInt(2) + 2; // max 3
            while(par.getOwnAttributes().size() < numberOfAttributes){
                Attribute randAttr = Attribute.values()[rand.nextInt(Attribute.values().length)];
                par.tryToAddToOwnAttributes(randAttr);
            }
            participants.add(par);
        }
        System.out.println("Participants have been set up");
    }

    private static void makeMatches(){
        while(!participants.isEmpty()){
            int firstIndex = rand.nextInt(participants.size());
            Participant first = participants.remove(firstIndex);
            int secondIndex = rand.nextInt(participants.size());
            Participant second = participants.remove(secondIndex);
            pairs.add(new ParticipantPair(first, second));
        }
    }

    private static void evaluatePairs(){
        for( ParticipantPair pair : pairs){
            System.out.println(pair.getOne().getName()
                    + " evaluated " + pair.getOther().getName()
                    + " for " + pair.getOne().evaluateOther(pair.getOther().getOwnAttributes()));
            System.out.println(pair.getOther().getName()
                    + " evaluated " + pair.getOne().getName()
                    + " for " + pair.getOther().evaluateOther(pair.getOne().getOwnAttributes()));
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
