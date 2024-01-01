package org.example.polymorphism2.interface1;

public class Chicken extends Animal implements Fly {
    @Override
    public void sound() {
        System.out.println("Cluck!");
    }

    @Override
    public void fly() {
        System.out.println("Flying Chicken!");
    }
}
