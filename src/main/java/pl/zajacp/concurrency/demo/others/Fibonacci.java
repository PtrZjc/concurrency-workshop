package pl.zajacp.concurrency.demo.others;

import lombok.AllArgsConstructor;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;


/*
Together: Fork and Join
The idea is to use fork() to submit tasks for asynchronous execution and then call join() to collect the results into a final answer. The ForkJoinPool's work-stealing algorithm ensures that threads that run out of things to do can steal tasks from other threads that are still busy. This makes it an efficient way of handling parallel workloads.

fork() is used to split the bigger problem into smaller problems.
join() is used to collect the results of these smaller problems to form a result for the bigger problem.
 */

@AllArgsConstructor
public class Fibonacci extends RecursiveTask<Integer> {
    final int n;

    @Override
    protected Integer compute() {
        System.out.printf("Computing Fibonacci(%d) in thread %s%n", n, Thread.currentThread().getName());

        if (n <= 1) {
            return n;
        }

        Fibonacci f1 = new Fibonacci(n - 1);
        f1.fork(); // submit f1 for asynchronous execution
        Fibonacci f2 = new Fibonacci(n - 2);
        int result = f2.compute() + f1.join(); // f1.join() waits for the f1 task to complete and then combine its result with that of f2.compute().

        System.out.printf("Computed Fibonacci(%d) = %d in thread %s%n", n, result, Thread.currentThread().getName());
        return result;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        System.out.println("Final result: " + pool.invoke(new Fibonacci(10)));
    }
}

