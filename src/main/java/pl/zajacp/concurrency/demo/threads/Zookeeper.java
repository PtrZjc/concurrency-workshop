package pl.zajacp.concurrency.demo.threads;

import lombok.SneakyThrows;

public class Zookeeper {
    public static void main(String[] unused) {
        var feedingJob = new Thread(Zookeeper::feedAnimals);
        feedingJob.setDaemon(true);
        feedingJob.start();
        System.out.println("Zoo is closing");
    }

    @SneakyThrows
    private static void feedAnimals() {
        Thread.sleep(3_000);
        System.out.println("All animals fed!");
    }
}
