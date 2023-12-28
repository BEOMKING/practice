package org.example.extends1.super1;

public class Parent {
    protected String value = "Parent";
    protected String name;

    public Parent() {
        System.out.println("부모 생성자");
        this.name = "Parent";
    }

    public void hello() {
        System.out.println("Hello from " + value);
    }
}
