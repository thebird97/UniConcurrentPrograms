package eightSynchronizers.fx;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class FXExchange {

    private static final Random random = new Random();
    private static final int LIMIT = 8;
    private static final AtomicBoolean isOver = new AtomicBoolean(false);
    private static final ExecutorService executor = Executors.newFixedThreadPool(LIMIT);
    private static final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {

        workers.add(new Worker(Currency.ABC, Currency.DEF, 50));
        workers.add(new Worker(Currency.DEF, Currency.XYZ, 70));
        workers.add(new Worker(Currency.XYZ, Currency.ABC, 100));

        workers.forEach(w -> executor.submit(w::startWorking));

        for(int i = 0; i < LIMIT - workers.size(); ++i){
            executor.submit(FXExchange::customerAction);
        }

        sleepForMsec(10000);
        isOver.set(true);
        workers.forEach(w -> w.finishWorking());
        executor.shutdownNow();
        System.out.println("Final results:");
        System.out.println("ABC: " + Portfolio.getPortfolio().getHoldings(Currency.ABC));
        System.out.println("DEF: " + Portfolio.getPortfolio().getHoldings(Currency.DEF));
        System.out.println("XYZ: " + Portfolio.getPortfolio().getHoldings(Currency.XYZ));


    }

    private static void customerAction(){
        while(!isOver.get()){
            ExchangeRequest req = generateRequest();
            Optional<Worker> worker = workers.stream().filter(w -> w.canProcess(req)).findFirst();
            worker.ifPresentOrElse(w -> w.submitRequest(req),
                    () -> System.out.println("Couldnt process " + req.toString()));
            sleepForMsec(2000);
        }
    }

    private static ExchangeRequest generateRequest(){
        int randFrom = random.nextInt(3);
        int randTo = random.nextInt(3);
        while(randTo == randFrom){
            randTo = random.nextInt(3);
        }
        int randAmount = 500 + random.nextInt(2000);
        boolean isBonus = random.nextInt() % 5 == 0; // 20% chance
        return new ExchangeRequest(
                Currency.values()[randFrom],
                Currency.values()[randTo],
                randAmount,
                isBonus);
    }

    private static void sleepForMsec(int sleepTime) {
        try {
            TimeUnit.MILLISECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            // dont care
        }
    }
}
