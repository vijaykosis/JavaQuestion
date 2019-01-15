package completeablefutureExample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class MainApp {

    private static ArrayList<String> numbers = new ArrayList<String>(Arrays.asList("0", "1", "2"));

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //testCompose();
        testCombine();
        System.out.println("Requesting");

      /*  CompletableFuture<String> future = createCF(3);

        future = future.thenApply(Integer::parseInt)
                .thenApply(r -> r * 2 * Math.PI)
                .thenApply(r->r.toString())
                .thenApply(s -> "apply>> " + s)
//				.exceptionally(ex -> "Error: " + ex.getMessage())
                .handle((result, ex) -> {
                    if (result != null) {
                        return result;
                    } else {
                        return "Error handling: " + ex.getMessage();
                    }
                });

        // future.complete("foo");
        future.thenAccept(result -> System.out.println("accept: " + result));

        // other statements
        for (int i = 1; i <= 3; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("running outside... " + i + " time");
        }

        // future.complete("foo");
        String contents = future.get();
        System.out.println("Future result: " + contents);*/
    }

    private static CompletableFuture<String> createCF(int index) {
        return CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    System.out.println("inside future: waiting for detecting...");
                    for (int i = 1; i <= 5; i++) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("running inside Future... " + i + " sec");
                    }
                    System.out.println("inside future: done...");

                    return numbers.get(index);
                } catch (Throwable e) {
                    return "not detected";
                }
            }
        });
    }

    private static void testCompose() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future = createCF(2); // inside future
        CompletableFuture<String> combinedFuture = future.thenCompose(MainApp::calculateCF);

        combinedFuture.thenAccept(result -> System.out.println("accept: " + result));
        // check results
        System.out.println("Future result>> " + future.get());
        System.out.println("combinedFuture result>> " + combinedFuture.get());
    }

    private static CompletableFuture<String> calculateCF(String s) {

        return CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println("> inside new Future");
                return "new Completable Future: " + s;
            }
        });
    }

    private static void testCombine() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future = createCF(1); // future 1
        CompletableFuture<String> newFuture = createCF(2); // future 2

        CompletableFuture<String> combinedFuture = future.thenCombine(newFuture, MainApp::appendString);

        // check results
        System.out.println("Future result>> " + future.get());
        System.out.println("newFuture result>> " + newFuture.get());
        System.out.println("combinedFuture result>> " + combinedFuture.get());
    }

    private static String appendString(String a, String b) {
        return a + " now appended to " + b;
    }

}