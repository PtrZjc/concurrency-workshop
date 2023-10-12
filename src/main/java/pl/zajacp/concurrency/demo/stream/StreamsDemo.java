package pl.zajacp.concurrency.demo.stream;

import lombok.SneakyThrows;

import java.util.stream.Stream;
public class StreamsDemo {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Stream.of(1, 2, 3, 4, 5)
                .peek(  StreamsDemo::peekAndSleep)
                .parallel()
                .forEach(s -> System.out.printf("Finished: %s in thread %s %n", s, Thread.currentThread().getName()));

        var timeTaken = (System.currentTimeMillis() - start) / 1000.0 ;
        System.out.println("\nTime: " + timeTaken + " seconds");
    }

    @SneakyThrows
    private static void peekAndSleep(Integer s) {
        System.out.printf("Peek %s in thread %s %n", s, Thread.currentThread().getName());
        Thread.sleep(500);
    }
}
//parallel, sequential, skip, forEachOrdered
