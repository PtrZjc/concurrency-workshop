package pl.zajacp.concurrency.demo.concurrency.api;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ZooFutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {

            Callable<Integer> ticketSellingTask = () -> {
                System.out.println("Starting to sell tickets...");
                Thread.sleep(2000); // Time needed to sell tickets
                return 42;
            };

            Future<Integer> futureTicketsSold = executorService.submit(ticketSellingTask);

            System.out.println("I'm free so I can clean the cages now...");
//            Thread.sleep(2100);

            if (!futureTicketsSold.isDone()) {
                System.out.println("Cleaning the cages.");
            } else {
                System.out.println("No time to clean the cages!");
            }

            Integer ticketsSold = futureTicketsSold.get(3, TimeUnit.SECONDS);
            System.out.println("Tickets sold: " + ticketsSold);

            if (futureTicketsSold.isDone()) {
                System.out.println("Ticket selling task is complete.");
            }
        }
    }
}
