package pl.zajacp.concurrency.demo.threads;

import java.util.stream.IntStream;

public class ZooActivitiesThread {

    public static void main(String[] args) {
        Runnable countAnimals = () ->
                System.out.println("Counting all the animals in the zoo");
        Runnable printAnimalProfiles = () ->
                IntStream.rangeClosed(1, 3).forEach(i ->
                        System.out.println("Printing profile of animal " + i)
                );

        System.out.println("Zoo day begins");
        new Thread(countAnimals).start();
        new Thread(printAnimalProfiles).start();
        new Thread(countAnimals).start();
        System.out.println("Zoo day ends");
    }
}
