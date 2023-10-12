package pl.zajacp.concurrency.demo.safety;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class MonkeyKeeper1 {
    private static int monkeyCount;

    private static void incrementAndReport() {
        System.out.print(++monkeyCount + " ");
    }

    public static void main(String[] args) {
        try (ExecutorService monkeyKeepers = Executors.newFixedThreadPool(10)) {

            IntStream.range(0, 10)
                    .forEach(i -> monkeyKeepers.submit(MonkeyKeeper1::incrementAndReport));
        }
    }
}