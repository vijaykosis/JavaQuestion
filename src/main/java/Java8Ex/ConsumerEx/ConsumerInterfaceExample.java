package Java8Ex.ConsumerEx;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerInterfaceExample {
    static void printMessage(String name) {
        System.out.println("Hello " + name);
    }

    static void printValue(int val) {
        System.out.println(val);
    }

    public static void main(String[] args) {
        // Referring method to String type Consumer interface   
        Consumer<String> consumer1 = ConsumerInterfaceExample::printMessage;
        consumer1.accept("John");   // Calling Consumer method  
        // Referring method to Integer type Consumer interface  
        Consumer<Integer> consumer2 = ConsumerInterfaceExample::printValue;
        consumer2.accept(12);   // Calling Consumer method

        ConsumerCall();
    }

    private static void ConsumerCall() {
        // Creating a list and adding values
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        // Referring method to String type Consumer interface

        Consumer<List<Integer>> consumer = ConsumerInterfaceExample::addList;
        consumer.accept(list);
    }

    public static void addList(List<Integer> list) {

        int result = list.stream().mapToInt(x -> x ).sum();
        System.out.println("Sum of list values: " + result);
    }
}  