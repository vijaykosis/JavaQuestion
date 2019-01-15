package MultiThreading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExample {

    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(10);

        Future<Integer> future = service.submit(new Task());
        try {
            System.out.println("get result from future");
            Integer res = future.get();
            System.out.println("result from future is::" + res);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

class Task implements Callable<Integer> {

    @Override
    public Integer call() {
        try {
            Thread.sleep(5000);
            return new Integer(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
