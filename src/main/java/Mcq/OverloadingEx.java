package Mcq;

public class OverloadingEx {
    public int foo(int a) {
        return 10;
    }

    public char foo() {
        return 'a';
    }

    public static void main(String[] args) {

        Integer s=null;
        System.out.println(s);
    }
}
