package sevenLocks.solution.fx;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class Worker {

    private final Currency from;
    private final Currency to;
    private final int cost;
    private final Random rand;
    private final Queue<ExchangeRequest> requests;
    private boolean isWorking;

    public Worker(Currency from, Currency to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
        this.rand = new Random();
        this.requests = new ConcurrentLinkedQueue<>();
        this.isWorking = true;
    }

    public void startWorking(){
        while(isWorking){
            if(!requests.isEmpty()){
                Portfolio.getPortfolio().doExchange(requests.poll(), cost);
            }
            sleepForRandom();
        }
    }

    public boolean canProcess(ExchangeRequest request){
        return request.getFrom() == this.from && request.getTo() == this.to;
    }

    public void submitRequest(ExchangeRequest req){
        requests.add(req);
    }

    public void finishWorking(){
        this.isWorking = false;
    }

    private void sleepForRandom() {
        try {
            int sleepTime = 1000 + rand.nextInt(1000);
            TimeUnit.MILLISECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            // dont care
        }
    }
}
