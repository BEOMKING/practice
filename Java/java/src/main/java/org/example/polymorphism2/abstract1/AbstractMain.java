package org.example.polymorphism2.abstract1;

import java.util.List;

public class AbstractMain {
    public static void main(String[] args) {
        Animal dog = new Dog();
        Animal cat = new Cat();
        Animal caw = new Caw();

        List<Animal> animals = List.of(dog, cat, caw);

        for (Animal animal : animals) {
            animal.sound();
        }
    }
}
