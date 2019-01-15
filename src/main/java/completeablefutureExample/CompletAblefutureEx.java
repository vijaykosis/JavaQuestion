package completeablefutureExample;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletAblefutureEx {
    public static void main(String args[]) {
        System.out.println("Thread Processing start....");
        CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("Inside Run .......");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("I'll run in a separate thread than the main thread.");
            }
        });
        try {
            future.get();
        } catch (Exception e) {
        }
    }
}
