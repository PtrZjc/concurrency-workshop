package pl.zajacp.concurrency.demo.safety;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class LionKeeper {
    Random random = new Random();

    @SneakyThrows
    private void removeLions() {
        Thread.sleep(random.nextInt(100));
        System.out.println("Removing lions by " + Thread.currentThread().getName());
    }

    @SneakyThrows
    private void cleanPen() {
        Thread.sleep(random.nextInt(100));
        System.out.println("Cleaning the pen by " + Thread.currentThread().getName());
    }

    @SneakyThrows
    private void addLions() {
        Thread.sleep(random.nextInt(100));
        System.out.println("Adding lions by " + Thread.currentThread().getName());
    }

    public void performTask() {
        removeLions();
        cleanPen();
        addLions();
    }

    public static void main(String[] args) {
        try(var service = Executors.newFixedThreadPool(4)) {
            var keeper = new LionKeeper();
            IntStream.range(0,4)
                    .forEach(i -> service.submit(keeper::performTask));
        }
    }
}
