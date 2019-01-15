package completeablefutureExample;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsynchronousExceptions {
    public static void main(final String[] args) throws InterruptedException {
        for (final boolean failure : new boolean[]{true}) {

            CompletableFuture<Integer> x = CompletableFuture.supplyAsync(() -> {
                if (failure) {
                    throw new RuntimeException("Oops, something went wrong");
                }
                return 42;
            });

            try {
                // Blocks (avoid this in production code!), and either returns the promise's value, or...
                System.out.println(x.get());
                System.out.println("isCompletedExceptionally::::get = " + x.isCompletedExceptionally());
                // Output[failure=false]: 42
                // Output[failure=false]: isCompletedExceptionally = false
            } catch (Exception e) {
                // ... rethrows the RuntimeException wrapped as an ExecutionException
                System.out.println(e.getMessage());
                System.out.println(e.getCause().getMessage());
                System.out.println("isCompletedExceptionally ::::::= " + x.isCompletedExceptionally());
                // Output[failure=true]:  java.lang.RuntimeException: Oops, something went wrong
                // Output[failure=true]:  Oops, something went wrong
                // Output[failure=true]:  isCompletedExceptionally = true
            }
        }
    }
}