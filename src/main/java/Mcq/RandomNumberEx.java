package Mcq;

import java.util.Random;

public class RandomNumberEx {
    public static void main(String[] args) {

        Random random = new Random();

        for (int i=0;i<90;i++)
        {
            int num = random.nextInt(90);


        }





    }
}

class Employee {

    private int id;
    private String name;
    private String surName;

    public Employee(int id, String name, String surName) {
        this.id = id;
        this.name = name;
        this.surName = surName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }
}
