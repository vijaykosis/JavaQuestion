package DesignPatteren.AbstractFactoryDesignPatternEx;

public class ComputerFactory {
    public static Computer getComputer(ComputerAbstractFactory factory) {
        return factory.createComputer();
    }
}
