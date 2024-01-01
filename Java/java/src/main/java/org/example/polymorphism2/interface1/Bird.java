package org.example.polymorphism2.interface1;

public class Bird extends Animal implements Fly {
    @Override
    public void sound() {
        System.out.println("Chirp!");
    }

    @Override
    public void fly() {
        System.out.println("Flying Bird!");
    }
}
