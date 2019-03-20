package DesignPatteren.StrategyPatteren;

public class StrategyPatternDemo {
    public static void main(String[] args) {
        Context context = new Context(new OperationAdd());
        System.out.println("OperationAdd(10,20)::" + context.executeStrategy(10, 20));

        context = new Context(new OperationMultiply());
        System.out.println("OperationMultiply(10,20)::" + context.executeStrategy(10, 20));

        context = new Context(new OperationSubtract());
        System.out.println("OperationSubtract(10,20)::" + context.executeStrategy(10, 20));


    }
}
