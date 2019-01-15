package completeablefutureExample;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class CompletAbleFutureThenCombineEx {
    public static void main(String args[]) {

        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() ->
        {
            int x = ThreadLocalRandom.current().nextInt(0, 5);
            System.out.println("Main Stage::" + x);
            return x;
        });

        try {
            cf.get();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
