package sevenLocks.solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

// Feladat:
// A teraszok kinyitasat kovetoen hirtelen mindenki el szeretne menni kocsmazni.
// Jelen kocsmaban csak egy koedukalt wc van, ahol a social distancing betartasa miatt
// maximum 2 ember allhat sorba. Egy 8 fos tarsasag ugy gondolta, hogy elmegy,
// es felvaltva sort iszik, vagy a mosdot hasznalja.
// 3 fele sor van, Arany Aszok, Kobanyai es Dreher. Mindegyik sor kulon csapbol jon,
// egy csapon egyszerre egy embert tudnak a pultosok kiszolgalni - oldjuk meg lockokkal!
// Minden vendeg a customerAction-t hajtja vegre, kulon szalon - ehhez hasznaljunk Executort!
// A wcben sorban allo egyeneket helyezzuk BlockingQueue-ba!
// Implementaljuk a useToilet fuggvenyt ugy, hogy amint felszabadul a terep, es all valaki a sorban,
// az egybol hasznalhassa. Hasznaljunk wait-notify mechanizmust, hogy jelezzuk a tryToUseToilet metodusnak,
// hogy a wc hasznalata sikeresen megtortent!
// Miutan bejelentik, hogy bezar a kocsma, akik a wc sorban allnak, meg elvegezhetik dolgukat, es
// csak utana kell hazamenniuk.
public class PubStudentVersion {

    private static final int CLOSING_TIME = 10000;
    private static final Random random = new Random();
    private static AtomicBoolean isClosed = new AtomicBoolean(false);
    private static BlockingQueue<String> toiletLine = new LinkedBlockingDeque<>(2);
    private static ExecutorService customerExecutor = Executors.newFixedThreadPool(9);
    private static List<String> customers = new ArrayList<>();

    private static int[] beerCounter = {0, 0, 0}; //aszok, kobanyai, dreher

    private static final ReentrantLock aszokLock = new ReentrantLock();
    private static final ReentrantLock kobanyaiLock = new ReentrantLock();
    private static final ReentrantLock dreherLock = new ReentrantLock();

    public static void main(String[] args) {

        customers.add("Walter");
        customers.add("Jesse");
        customers.add("Saul");
        customers.add("Hank");
        customers.add("Marie");
        customers.add("Skyler");
        customers.add("Gus");
        customers.add("Mike");

        // Hasznaljunk executort a customerAction es useToilet metodusok kulon szalon torteno futtatasahoz!

        sleepForMsec(CLOSING_TIME);
        isClosed.set(true);

        // Miutan az executor shutdown-ol, irassuk ki, hogy melyik sorbol mennyi fogyott
        System.out.println("Aszok: " + beerCounter[0] + "\nKobanyai: " + beerCounter[1] + "\nDreher: " + beerCounter[2]);

    }

    private static void customerAction(String name){
        while(!isClosed.get()){
            int rand = random.nextInt();
            if(rand % 2 == 0){
                orderBeer(name);
            } else {
                tryToUseToilet(name);
            }
            sleepForMsec(2000);
        }
        System.out.println(name + " goes home");
    }

    private static void orderBeer(String name){
        BeerType type = getBeerType(name);
        System.out.println(name + " orders " + type.toString());
        if(type == BeerType.ARANY_ASZOK)
            getBeer(aszokLock, name, 0);
        if(type == BeerType.KOBANYAI)
            getBeer(kobanyaiLock, name, 1);
        if(type == BeerType.DREHER)
            getBeer(dreherLock, name, 2);
    }

    private static void getBeer(ReentrantLock lock, String name, int index){
        // Implementaljuk a lock hasznalatat!
        boolean success = true;
        System.out.println(name + " is waiting for the beer");
        sleepForMsec(1000);
        beerCounter[index]++;
        System.out.println(success ? name + " got the beer" : name + " didnt get the beer");
    }

    private static BeerType getBeerType(String name){
        int rand = random.nextInt(3);
        return BeerType.values()[rand];
    }

    private static void tryToUseToilet(String name){
        // Emberunk alljon be a sorba, ha van meg hely, ha nincs, fusson le az else ag
        boolean canUse = true;
        if(canUse){
            System.out.println(name + " is waiting for the toilet");
            // Implementaljuk a wait-notify mechanizmust
            System.out.println(name + " finished using the toilet");
        } else {
            System.out.println(name + " wanted to use the toilet, but line was full");
        }
    }

    private static void useToilet(){
        while(!isClosed.get() || !toiletLine.isEmpty()) {
            // Varjunk 1 masodpercet, hogy sorban all-e valaki
            // Amennyiben 1 masodpercen belul senki sem kivanja hasznalni a mosdot,
            // probaljuk ujra a varakozast
            sleepForMsec(100 + random.nextInt(900));
            // Ha valaki volt a sorban, sleep utan valositsuk meg a wait-notify mechanizmust
            // (lehet a string objektumon)
        }
    }

    private static void sleepForMsec(int msec) {
        try {
            TimeUnit.MILLISECONDS.sleep(msec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
