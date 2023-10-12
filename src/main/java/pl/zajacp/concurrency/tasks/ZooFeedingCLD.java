package pl.zajacp.concurrency.tasks;

import lombok.SneakyThrows;
import java.util.concurrent.CountDownLatch;

public class ZooFeedingCLD {

    @SneakyThrows
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);

        Thread lionThread = new Thread(() -> animalFeeding("Lion", 2000, latch));
        Thread elephantThread = new Thread(() -> animalFeeding("Elephant", 3000, latch));
        Thread tigerThread = new Thread(() -> animalFeeding("Tiger", 1000, latch));

        Thread zookeeperThread = new Thread(() -> zookeeperActivity(latch));

        zookeeperThread.start();

        lionThread.start();
        elephantThread.start();
        tigerThread.start();
    }

    @SneakyThrows
    private static void animalFeeding(String animal, long waitTime, CountDownLatch latch) {
        System.out.println(animal + " is waiting to be fed.");
        Thread.sleep(waitTime);
        System.out.println(animal + " has been fed.");
        latch.countDown();
    }

    @SneakyThrows
    private static void zookeeperActivity(CountDownLatch latch) {
        System.out.println("Zookeeper is waiting for all animals to be fed.");
        latch.await();
        System.out.println("All animals have been fed. Zookeeper's task is complete.");
    }
}
