package eightSynchronizers.fx;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Semaphore;

public class Portfolio {

    private static Portfolio instance;
    private final Map<String, Integer> currencyHoldings = new HashMap<>(Map.of(
            Currency.ABC.name(), 10000,
            Currency.DEF.name(), 20000,
            Currency.XYZ.name(), 30000
    ));
    private final Semaphore semaphore;

    private Portfolio(){
        this.semaphore = new Semaphore(1);
    }

    // lazy init singleton
    public static Portfolio getPortfolio(){
        if(Objects.isNull(instance))
            instance = new Portfolio();
        return instance;
    }

    public void doExchange(ExchangeRequest request, int fee){
        try {
            semaphore.acquire();
            System.out.println("Starting " + request.toString() + " for " + fee + request.getFrom().name());
            // take fee
            currencyHoldings.put(request.getFrom().name(), currencyHoldings.get(request.getFrom().name()) - fee);
            // convert and add to destination ccy
            double secondCcyValue = request.getAmount() * Currency.computeRate(request.getFrom(), request.getTo());
            int secondCcyValueRounded = (int)Math.round(secondCcyValue);
            currencyHoldings.put(request.getTo().name(), currencyHoldings.get(request.getTo().name()) + secondCcyValueRounded);
            // take from source ccy
            currencyHoldings.put(request.getFrom().name(), currencyHoldings.get(request.getFrom().name()) - request.getAmount());
            // add bonus if applicable
            if(request.isBonus()) {
                currencyHoldings.put(request.getTo().name(), currencyHoldings.get(request.getTo().name()) + 1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public int getHoldings(Currency currency){
        Integer value = currencyHoldings.get(currency.name());
        if(Objects.isNull(value)) {
            System.out.println("ERROR: Unknown currency - returning 0");
            return 0;
        }
        return value;
    }

}
