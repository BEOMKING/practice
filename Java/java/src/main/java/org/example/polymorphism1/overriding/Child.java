package org.example.polymorphism1.overriding;

public class Child extends Parent {
    public String value = "Child";

    @Override
    public void hello() {
        System.out.println("Hello from " + value);
    }

    public void call() {
        System.out.println("this.value = " + this.value);
        System.out.println("super.value = " + super.value);

        this.hello();
        super.hello();
    }
}
