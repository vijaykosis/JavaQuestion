package Mcq;

class Adder {

    static int add(int a, double b) {
        System.out.print("Add 1::::");

        return a + (int) b;
    }

    static double add(double a, int b) {
        System.out.print("Add 2::::");
        return a + b;
    }

    public static void main(String args[]) {
        System.out.println(Adder.add(11, 11.2));
        System.out.print(Adder.add(12.3, 12));
    }
}