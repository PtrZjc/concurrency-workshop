package pl.zajacp.concurrency.tasks;

import lombok.SneakyThrows;

public class ZooAnnouncement {

    @SneakyThrows
    public static void main(String[] args) {
        Thread openThread = new Thread(ZooAnnouncement::openZoo);
        Thread closeThread = new Thread(ZooAnnouncement::closeZoo);

        openThread.start();
        openThread.join();
        closeThread.start();
    }

    @SneakyThrows
    private static void openZoo() {
        System.out.println("Announcement: The zoo is opening!");
        Thread.sleep(3000);
        System.out.println("Announcement: The zoo is now open!");
    }

    @SneakyThrows
    private static void closeZoo() {
        System.out.println("Announcement: The zoo is closing!");
        Thread.sleep(3000);
        System.out.println("Announcement: The zoo is now closed!");
    }
}
