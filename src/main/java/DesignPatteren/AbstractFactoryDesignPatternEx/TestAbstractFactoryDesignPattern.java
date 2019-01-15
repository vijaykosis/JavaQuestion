package DesignPatteren.AbstractFactoryDesignPatternEx;

public class TestAbstractFactoryDesignPattern {

    public static void main(String args[]) {
        Computer pc = ComputerFactory.getComputer(new PCFactory("corcare", "samsung", "Intel"));
        System.out.println(pc);

        Computer server = ComputerFactory.getComputer(new ServerFactory("16 GB", "1 TB", "2.9 GHz"));
        System.out.println(server);

    }
}
