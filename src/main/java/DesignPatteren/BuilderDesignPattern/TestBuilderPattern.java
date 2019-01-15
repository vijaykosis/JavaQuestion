package DesignPatteren.BuilderDesignPattern;

public class TestBuilderPattern {

    public static void main(String[] args) {
        Computer comp = new Computer.ComputerBuilder(
                "500 GB", "2 GB", true, true).setBluetoothEnabled(true)
                .setGraphicsCardEnabled(true).setHDD("SAMSUNG").setRAM("HP 4GB").build();
        System.out.println(comp);
    }
}
