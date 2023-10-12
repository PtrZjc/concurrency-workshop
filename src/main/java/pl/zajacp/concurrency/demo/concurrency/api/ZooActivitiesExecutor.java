package pl.zajacp.concurrency.demo.concurrency.api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class ZooActivitiesExecutor {

    public static void main(String[] args) {
        Runnable countAnimals = () ->
                System.out.println("Counting all the animals in the zoo");
        Runnable printAnimalProfiles = () ->
                IntStream.rangeClosed(1, 3).forEach(i ->
                        System.out.println("Printing profile of animal " + i)
                );

        try (ExecutorService service = Executors.newSingleThreadExecutor()) {
            System.out.println("Zoo day begins");
            service.execute(countAnimals);
            service.execute(printAnimalProfiles);
            service.execute(countAnimals);
            System.out.println("Zoo day ends");
        }
    }
}
