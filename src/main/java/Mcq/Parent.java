package Mcq;// A Simple Java program to demonstrate
// Overriding and Access-Modifiers 

class Parent {
    // private methods are not overridden
  /*  private void m1() {
        System.out.println("From parent m1()");
    }
*/
    void m2() {
        System.out.println("From parent m2()");
    }

    int m2(int a) {
        System.out.println("From parent m2()");
        return 1;
    }

}

class Child extends Parent {
    // new m1() method
    // unique to Child class
  /*  private void m1() {
        System.out.println("From child m1()");
    }
*/
    // overriding method
    // with more accessibility
    @Override
    protected void m2() {
        System.out.println("From child m2()");
    }

}

// Driver class 
class Main {
    public static void main(String[] args) {
        Parent obj1 = new Parent();
        obj1.m2();
        Parent obj2 = new Child();
        obj2.m2();

    }
} 
