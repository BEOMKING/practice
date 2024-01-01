package org.example.extends1.super1;

public class SuperMain {
    public static void main(String[] args) {
        System.out.println("--부모 클래스--");
        Parent child = new Child("Child", 10);
        System.out.println("child.name = " + child.name);
        System.out.println("--자식 클래스--");
        Child child2 = (Child) child;
        child2.call();
        System.out.println("child2.name = " + child2.name);
        System.out.println("child.age = " + child2.age);
    }
}
