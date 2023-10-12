package pl.zajacp.concurrency.tasks;

import lombok.SneakyThrows;

import java.util.concurrent.CyclicBarrier;

public class ZooFeedingCB {

    @SneakyThrows
    public static void main(String[] args) {
        //4 parties - 3 animals and 1 zookeeper
        CyclicBarrier barrier = new CyclicBarrier(4);

        Thread lionThread = new Thread(() -> animalActivity("Lion", 2000, barrier));
        Thread elephantThread = new Thread(() -> animalActivity("Elephant", 3000, barrier));
        Thread tigerThread = new Thread(() -> animalActivity("Tiger", 1000, barrier));

        Thread zookeeperThread = new Thread(() -> zookeeperActivity(barrier));

        lionThread.start();
        elephantThread.start();
        tigerThread.start();
        zookeeperThread.start();
    }

    @SneakyThrows
    private static void animalActivity(String animal, long waitTime, CyclicBarrier barrier) {
        System.out.println(animal + " is waiting to be fed.");
        Thread.sleep(waitTime);
        System.out.println(animal + " has been fed.");
        barrier.await();
    }

    @SneakyThrows
    private static void zookeeperActivity(CyclicBarrier barrier) {
        System.out.println("Zookeeper is waiting for all animals to be fed.");
        barrier.await();
        System.out.println("All animals have been fed. Zookeeper's task is complete.");
    }
}
