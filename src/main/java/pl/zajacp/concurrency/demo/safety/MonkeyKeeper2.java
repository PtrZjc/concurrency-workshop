package pl.zajacp.concurrency.demo.safety;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class MonkeyKeeper2 {
    private static final Object cage = new Object();
    private static int monkeyCount;

    private static void incrementAndReport() {
        synchronized (cage) {
            System.out.print(++monkeyCount + " ");
        }
    }
    public static void main(String[] args) {
        try (ExecutorService monkeyKeepers = Executors.newFixedThreadPool(10)) {

            IntStream.range(0, 10)
                    .forEach(i -> monkeyKeepers.submit(MonkeyKeeper2::incrementAndReport));
        }
    }
}

