package pl.zajacp.concurrency.tasks;

import lombok.Data;
import lombok.SneakyThrows;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class FeedingAnimalsPt2Opt {

    public static Runnable feedAnimal(String animalType, ConfigurableFoodBowl foodBowl) {
        return () -> {
            System.out.println(animalType + " wants to eat.");
            foodBowl.use(animalType);
        };
    }

    public static Runnable zookeeperRefillingBowlTask(ConfigurableFoodBowl foodBowl) {
        return () -> {
            while (true) {
                synchronized (foodBowl){
                    if (foodBowl.getEatCount().get() >= foodBowl.getBowlSize()) {
                        foodBowl.refill();
                    }
                }
            }
        };
    }

    public static void main(String[] args) {
        ConfigurableFoodBowl foodBowl = new ConfigurableFoodBowl(2);
        Thread zooKeeper = new Thread(zookeeperRefillingBowlTask(foodBowl));
        zooKeeper.setDaemon(true);
        zooKeeper.start();

        try (var executor = Executors.newFixedThreadPool(4)) {
            executor.submit(feedAnimal("Lion", foodBowl));
            executor.submit(feedAnimal("Elephant", foodBowl));
            executor.submit(feedAnimal("Giraffe", foodBowl));
        }
    }
}

@Data
class ConfigurableFoodBowl {
    private final int bowlSize;
    private final AtomicInteger eatCount = new AtomicInteger(0);

    @SneakyThrows
    public synchronized void use(String animalName) {
        while (eatCount.get() >= bowlSize) wait();
        // When an animal tries to eat but finds that bowl is empty thread enters
        // a waiting state by invoking wait() until the bowl is refilled

        System.out.println(animalName + " is eating.");
        Thread.sleep((long) (Math.random() * 100));
        eatCount.incrementAndGet();
        System.out.println(animalName + " has finished eating.");
    }

    public void refill() {
        eatCount.set(0);
        System.out.println("Food bowl has been refilled.");
        notifyAll();
        // Once the ZooKeeper refills the bowl, notifyAll() is called to
        // wake up any animal threads that were waiting to eat.
    }
}