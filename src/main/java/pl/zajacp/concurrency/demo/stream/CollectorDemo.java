package pl.zajacp.concurrency.demo.stream;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorDemo {
    private final static Random random = new Random();

    public static void main(String[] args) {

        Map<String, Long> letterCount = Stream.generate(CollectorDemo::getRandomLowercaseLetter)
                .parallel()
                .limit(10)
                .collect(Collectors.groupingByConcurrent(CollectorDemo::returnSelfCheckingThread, Collectors.counting()));

        System.out.println(letterCount);
    }

    private static String getRandomLowercaseLetter() {
        return String.valueOf((char) (random.nextInt(26) + 'a'));
    }

    private static String returnSelfCheckingThread(String value) {
        System.out.printf("Handling \"%s\" by thread %s%n", value, Thread.currentThread().getName());
        return value;
    }
}
