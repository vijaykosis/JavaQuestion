package MultiThreading;

public class UncaughtExceptionHandlerEx {

    public static void main(String args[]) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(new Handler());
        throw new Exception("A test exception");
    }
}

class Handler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("exception caught:" + e+"Thread "+t.getName());

    }
}
