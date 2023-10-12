package pl.zajacp.concurrency.demo.threads;

import java.util.stream.IntStream;

public class CheckZooVisitors1 {
    private static int visitorCount = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            IntStream.range(0, 1_000_000).forEach(i -> visitorCount++);
            System.out.println("Visitor counting finished");
        }).start();

        while (visitorCount < 1_000_000) {
            System.out.println("Waiting for more visitors");
        }
        System.out.println("Visitor milestone reached: " + visitorCount);
    }
}