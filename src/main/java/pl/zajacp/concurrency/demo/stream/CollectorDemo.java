package pl.zajacp.concurrency.demo.stream;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorDemo {

    public static void main(String[] args) {
        Random random = new Random();

        Map<String, Long> letterCount = Stream.generate(() -> String.valueOf((char) (random.nextInt(26) + 'a')))
                .parallel()
                .limit(10)
                .collect(Collectors.groupingByConcurrent(CollectorDemo::returnSelfCheckingThread, Collectors.counting()));
    }

    private static String returnSelfCheckingThread(String value) {
        System.out.printf("Handling \"%s\" by thread %s%n", value, Thread.currentThread().getName());
        return value;
    }
}
