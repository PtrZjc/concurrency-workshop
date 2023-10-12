package pl.zajacp.concurrency.demo.safety;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class LionKeeperCDL {
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

    @SneakyThrows
    public void performTask(CountDownLatch latch1, CountDownLatch latch2) {
        removeLions();

        latch1.countDown();
        latch1.await();

        cleanPen();

        latch2.countDown();
        latch2.await();

        addLions();
    }

    public static void main(String[] args) {
        var keeperNumber = 4;
        var latch1 = new CountDownLatch(keeperNumber);
        var latch2 = new CountDownLatch(keeperNumber);

        try (var service = Executors.newFixedThreadPool(keeperNumber)) {
            var keeper = new LionKeeperCDL();

            IntStream.range(0, 4)
                    .forEach(i -> service.submit(() -> keeper.performTask(latch1, latch2)));
        }
    }
}