package pl.zajacp.concurrency.demo.threads;

import java.util.stream.IntStream;

public class CheckZooVisitors3 {
    private static int visitorCount = 0;

    public static void main(String[] args) {
        final var mainThread = Thread.currentThread();
        new Thread(() -> {
            IntStream.range(0, 1_000_000).forEach(i -> visitorCount++);
            System.out.println("Visitor counting finished");
            mainThread.interrupt();
        }).start();

        while (visitorCount < 1_000_000) {
            System.out.println("Taking a break, not enough visitors yet");
            try {
                Thread.sleep(1_000); // 1 SECOND
                System.out.println("After a break, checking visitor count again.");
            } catch (InterruptedException e) {
                System.out.println("Urgent update! Checking visitor count again.");
            }
        }
        System.out.println("Visitor milestone reached: " + visitorCount);
    }
}