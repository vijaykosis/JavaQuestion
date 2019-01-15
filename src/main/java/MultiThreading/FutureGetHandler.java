package MultiThreading;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

final class MyTask implements Runnable {
    @Override
    public void run() {
        System.out.println("My task is started running...");
        // ...
        throw new ArithmeticException();
        // ...
    }
}

public class FutureGetHandler {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        Future<?> future = threadPool.submit(new MyTask());

        try {
            future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            // Extract the actual exception from its wrapper
            Throwable t = e.getCause();
            System.err.println("Uncaught exception is detected! " + t
                    + " st: " + Arrays.toString(t.getStackTrace()));
            // ... Handle the exception
        }
    }
}