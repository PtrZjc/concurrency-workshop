package pl.zajacp.concurrency.demo.threads;

import lombok.SneakyThrows;

public class ZooJoiningTasks {

    @SneakyThrows
    private static void feedAnimals() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Feeding Animal " + (i + 1));
            Thread.sleep(500);
        }
    }

    @SneakyThrows
    private static void cleanCages(Thread feedAnimalsThread) {
        feedAnimalsThread.join(); // Cleaning should only happen after feeding is done
        System.out.println("Clean Cages: Animals have been fed, starting cleaning.");
    }

    public static void main(String[] args) {
        Thread feedAnimalsThread = new Thread(ZooJoiningTasks::feedAnimals);
        Thread cleanCagesThread = new Thread(() -> cleanCages(feedAnimalsThread));

        feedAnimalsThread.start();
        cleanCagesThread.start();
    }
}
