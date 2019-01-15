package completeablefutureExample;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class AsynchronousLongComputeVsSlowStore {
    private static final ConcurrentMap<Integer, Integer> store = new ConcurrentHashMap<>();
    public static final Integer NO_VALUE = null;

    public static void main(final String[] args) {
        stopwatch("Scenario #1: first-time compute, value '42' not yet in the store. ETA: 3 seconds.", () -> {
            final int loadTime = 1;
            final int computeTime = 3;
            calcOrLoadSquareOfX(loadTime, computeTime, 42);
        });

        stopwatch("Scenario #2: second-time compute, value '42' now in the store. ETA: 1 second.", () -> {
            final int loadTime = 1;
            final int computeTime = 3;
            calcOrLoadSquareOfX(loadTime, computeTime, 42);
        });

        stopwatch("Scenario #3: same sleep time for both, to see how futures' state is handled. ETA: 3 seconds.", () -> {
            final int loadTime = 3;
            final int computeTime = 3;
            calcOrLoadSquareOfX(loadTime, computeTime, 1337);
        });

        stopwatch("Scenario #4: connectivity to the store times out. ETA: 3 seconds.", () -> {
            final int loadTime = 5;
            final int computeTime = 3;
            calcOrLoadSquareOfX(loadTime, computeTime, 11235);
        });

        stopwatch("Scenario #5: store throws exception. ETA: 0 seconds.", () -> {
            final int loadTime = 5;
            final int computeTime = 3;
            calcOrLoadSquareOfX(loadTime, computeTime, 31415, true);
        });
    }

    // Output:
    //
    // [main]: Scenario #1: first-time compute, value '42' not yet in the store. ETA: 3 seconds.
    // [ForkJoinPool.commonPool-worker-2]: Calculated 42 -> 1764.
    // [main]: Result: 1764
    // [main]: Elapsed time: 3 seconds.
    //
    // [main]: Scenario #2: second-time compute, value '42' now in the store. ETA: 1 second.
    // [ForkJoinPool.commonPool-worker-1]: Loaded 42 -> 1764.
    // [ForkJoinPool.commonPool-worker-1]: Calculation cancelled.
    // [main]: Result: 1764
    // [main]: Elapsed time: 1 seconds.
    //
    // [main]: Scenario #3: same sleep time for both, to see how futures' state is handled. ETA: 3 seconds.
    // [ForkJoinPool.commonPool-worker-1]: Calculated 1337 -> 1787569.
    // [main]: Result: 1787569
    // [main]: Elapsed time: 3 seconds.
    //
    // [main]: Scenario #4: connectivity to the store times out. ETA: 3 seconds.
    // [ForkJoinPool.commonPool-worker-1]: Calculated 11235 -> 126225225.
    // [ForkJoinPool.commonPool-worker-1]: Load cancelled.
    // [main]: Result: 126225225
    // [main]: Elapsed time: 3 seconds.
    //
    // [main]: Scenario #5: store throws exception. ETA: 0 seconds.
    // [main]: Error: java.lang.RuntimeException: Oops, something went wrong
    // [main]: isCompletedExceptionally = true
    // [main]: Elapsed time: 0 seconds.

    private static void calcOrLoadSquareOfX(final int loadTime, final int computeTime, final int x) {
        calcOrLoadSquareOfX(loadTime, computeTime, x, false);
    }

    private static void calcOrLoadSquareOfX(final int loadTime, final int computeTime, final int x, final boolean failure) {
        CompletableFuture<Integer> calcSquareOfX = CompletableFuture.supplyAsync(() -> {
            sleep(computeTime, SECONDS); // Very expensive compute!
            final int value = x * x;
            store.put(x, value); // Cache the result for next time!
            return value;
        }).thenApply(name -> "Hello " + name)
                .thenApply(name->name.length());

        CompletableFuture<Integer> loadSquareOfX = CompletableFuture.supplyAsync(() -> {
            if (failure) {
                throw new RuntimeException("Oops, something went wrong");
            }
            sleep(loadTime, SECONDS); // Very slow, remote store!
            return store.get(x);
        });

        CompletableFuture<Integer> calcOrLoad = loadSquareOfX.applyToEitherAsync(calcSquareOfX, value -> {
            if (!loadSquareOfX.isDone() || (loadSquareOfX.isDone() && (value == NO_VALUE))) {
                value = get(calcSquareOfX); // Square-of-x not in store, we wait for end of calculation.
                println("Calculated " + x + " -> " + value + ".");
            } else {
                println("Loaded " + x + " -> " + value + ".");
            }

            // Cancel the remaining completable future to avoid wasting time and resources:
            if (calcSquareOfX.isDone() && !loadSquareOfX.isDone()) {
                // Store seems to be slow, we save I/O!
                // N.B.: the current CompletableFuture implementation does not interrupt when true is passed to cancel:
                if (loadSquareOfX.cancel(true)) {
                    println("Load cancelled.");
                }
            } else if (loadSquareOfX.isDone() && !calcSquareOfX.isDone()) {
                // Loading from store was successfully, we save CPU cycles!
                // N.B.: the current CompletableFuture implementation does not interrupt when true is passed to cancel:
                if (calcSquareOfX.cancel(true)) {
                    println("Calculation cancelled.");
                }
            }

            return value;
        });

        try {
            println("Result: " + calcOrLoad.get());
        } catch (ExecutionException | InterruptedException e) {
            println("Error: " + e.getMessage());
            println("isCompletedExceptionally = " + calcOrLoad.isCompletedExceptionally());
        }
    }

    private static <T> T get(final CompletableFuture<T> completableFuture) {
        try {
            return completableFuture.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sleep(final int duration, final TimeUnit unit) {
        try {
            unit.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void println(final String message) {
        System.out.println("[" + Thread.currentThread().getName() + "]: " + message);
    }

    private static void stopwatch(final String name, final Runnable action) {
        println(name);
        final long begin = System.currentTimeMillis();
        action.run();
        println("Elapsed time: " + (System.currentTimeMillis() - begin) / 1000 + " seconds.");
        System.out.println();
        sleep(1, SECONDS);
    }
}