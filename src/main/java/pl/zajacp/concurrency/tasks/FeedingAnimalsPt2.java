package pl.zajacp.concurrency.tasks;

import lombok.SneakyThrows;

import java.util.concurrent.Executors;

public class FeedingAnimalsPt2 {

    public static void main(String[] args) {
        FoodBowl foodBowl = new FoodBowl();

        Runnable feedLion = feedAnimal("Lion", foodBowl);
        Runnable feedElephant = feedAnimal("Elephant", foodBowl);
        Runnable feedGiraffe = feedAnimal("Giraffe", foodBowl);

        try (var service = Executors.newFixedThreadPool(3)) {
            service.submit(feedLion);
            service.submit(feedElephant);
            service.submit(feedGiraffe);
        }
    }

    public static Runnable feedAnimal(String type, FoodBowl foodBowl) {
        return () -> {
            System.out.println(type + " wants to eat.");
            foodBowl.use(type);
        };
    }
}

class FoodBowl {
    @SneakyThrows
    public synchronized void use(String animalName) {
        System.out.println(animalName + " is eating.");
        Thread.sleep((long) (Math.random() * 100));
        System.out.println(animalName + " has finished eating.");
    }
}