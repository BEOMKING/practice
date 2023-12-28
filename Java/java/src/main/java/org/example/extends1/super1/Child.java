package org.example.extends1.super1;

public class Child extends Parent {
    private String value = "Child";
    public String name;
    public int age;

    public Child(String name, int age) {
        super();
        System.out.println("자식 생성자");
        this.name = name;
        this.age = age;
    }

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
