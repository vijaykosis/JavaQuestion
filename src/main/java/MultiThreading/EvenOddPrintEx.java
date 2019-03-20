package MultiThreading;

public class EvenOddPrintEx extends Thread {

    public static int count = 0;
    public static int MAX = 10;
    static Object lock = new Object();

    int remainder;

    public EvenOddPrintEx(int i) {
        remainder = i;
    }

    public static void main(String[] args) {

        System.out.println(checkFinallyBlock());

        EvenOddPrintEx ex1 = new EvenOddPrintEx(0);
        EvenOddPrintEx ex2 = new EvenOddPrintEx(0);

        String s = new String("vijay");
        String s2 = new String("vijay");
        s.equals(s2);



        System.out.println(s.equals(s2));

        ex1.setName("Even");
        ex2.setName("Odd");

        ex1.start();
        ex2.start();


    }



    private static boolean checkFinallyBlock() {




        try {
            return true;

        } finally {
            return false;
        }
    }

    @Override
    public void run() {
        while (count < MAX) {
            synchronized (lock) {
                while (count % 2 != remainder) { // wait for numbers other than remainder
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " -->  " + count);
                count++;
                lock.notifyAll();
            }
        }
    }

}
