package pl.zajacp.concurrency.tasks.eating;

import lombok.SneakyThrows;

import java.util.concurrent.Executors;

public class FeedingAnimalsPt2 {

    public static void main(String[] args) {
        FoodBowl foodBowl = new FoodBowl();

        Runnable lion = createAnimal("Lion", foodBowl);
        Runnable elephant = createAnimal("Elephant", foodBowl);
        Runnable giraffe = createAnimal("Giraffe", foodBowl);

        try (var service = Executors.newFixedThreadPool(3)) {
            service.submit(lion);
            service.submit(elephant);
            service.submit(giraffe);
        }
    }

    public static Runnable createAnimal(String type, FoodBowl foodBowl) {
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