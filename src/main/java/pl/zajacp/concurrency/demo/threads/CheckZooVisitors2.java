package pl.zajacp.concurrency.demo.threads;

import java.util.stream.IntStream;

public class CheckZooVisitors2 {
    private static int visitorCount = 0;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            IntStream.range(0, 1_000_000).forEach(i -> visitorCount++);
            System.out.println("Visitor counting finished");
        }).start();

        while (visitorCount < 1_000_000) {
            System.out.println("Taking a break, not enough visitors yet");
            Thread.sleep(1_000); // 1 SECOND
        }
        System.out.println("Visitor milestone reached: " + visitorCount);
    }
}