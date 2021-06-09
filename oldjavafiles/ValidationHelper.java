package oldjavafiles;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class ValidationHelper {

    public static void measureTime(Runnable task){
        Instant start = Instant.now();
        task.run();
        Instant end = Instant.now();
        Duration runTime = Duration.between(start, end);
        System.out.println("Program ran for " + runTime.toMillis() + " ms");
    }

    public static void validateAll(List<Integer> numbers, int limit){
        validateQuantity(numbers,limit);
        validateOrder(numbers);
        validateNullValues(numbers);
    }

    public static void validateQuantity(List<Integer> numbers, int limit){
        if(numbers.size() == limit){
            System.out.println("All the numbers are stored");
            return;
        }
        System.out.println("Missing " + (limit - numbers.size()) + " numbers from the list");
    }

    public static void validateOrder(List<Integer> numbers){
        if(numbers.isEmpty()){
            System.out.println("List of numbers is empty.");
            return;
        }
        int counter = 0;
        int current = numbers.get(0);
        for(Integer i : numbers){
            if(Objects.isNull(i)) continue;
            if(current > i) counter++;
            current = i;
        }
        if(counter > 0 ){
            System.out.println("There are " + counter + " numbers in the wrong order");
        } else {
            System.out.println("Ordering seems fine");
        }
    }

    public static void validateNullValues(List<Integer> numbers){
//        int counter = 0;
//        for(Integer num : numbers){
//            if(Objects.isNull(num)) counter++;
//        }
        long counter = numbers.stream().filter(Objects::isNull).count();
        if(counter > 0 ){
            System.out.println("There are " + counter + " nulls in the list");
        } else {
            System.out.println("List is without null values");
        }
    }

}
