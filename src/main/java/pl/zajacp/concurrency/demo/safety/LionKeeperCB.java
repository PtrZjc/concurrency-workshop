package pl.zajacp.concurrency.demo.safety;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class LionKeeperCB {
    Random random = new Random();

    @SneakyThrows
    private void removeLions() {
        Thread.sleep(random.nextInt(100));
        System.out.println("Removing lions by " + Thread.currentThread().getName());
    }

    @SneakyThrows
    private void cleanPen() {
        Thread.sleep(random.nextInt(100));
        System.out.println("Cleaning the pen by" + Thread.currentThread().getName());
    }

    @SneakyThrows
    private void addLions() {
        Thread.sleep(random.nextInt(100));
        System.out.println("Adding lions by " + Thread.currentThread().getName());
    }

    public void performTask(CyclicBarrier c1, CyclicBarrier c2) {
        try {
            removeLions();
            c1.await();
            cleanPen();
            c2.await();
            addLions();
        } catch (InterruptedException | BrokenBarrierException e) {
            // oh well
        }
    }

    public static void main(String[] args) {
        var keeperNumber = 4;

        try (var service = Executors.newFixedThreadPool(keeperNumber)) {
            var c1 = new CyclicBarrier(keeperNumber, () -> System.out.println("*** Lions removed!"));
            var c2 = new CyclicBarrier(keeperNumber, () -> System.out.println("*** Pen Cleaned!"));
            var keeper = new LionKeeperCB();
            IntStream.range(0, 4)
                    .forEach(i -> service.submit(
                            () -> keeper.performTask(c1, c2))
                    );
        }
    }
}
