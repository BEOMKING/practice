package org.example.polymorphism2.interface1;

public class InterfaceMain {
    public static void main(String[] args) {
        final Dog dog = new Dog();
        final Bird bird = new Bird();
        final Chicken chicken = new Chicken();

        sound(dog);
        sound(bird);
        sound(chicken);

        fly(bird);
        fly(chicken);
    }

    private static void sound(final Animal animal) {
        animal.sound();
    }

    private static void fly(final Fly fly) {
        fly.fly();
    }
}
