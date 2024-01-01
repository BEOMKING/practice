package org.example.polymorphism1.overriding;

public class OverridingMain {
    public static void main(String[] args) {
        variable();
        overrideMethod();
    }

    /**
     * 변수는 오버라이딩 되지 않는다.
     */
    private static void variable() {
        System.out.println("start variableNumber");
        final Parent parent = new Child();
        System.out.println(parent.value);

        final Child child = new Child();
        System.out.println(child.value);
    }

    /**
     * 메소드는 오버라이딩 된다.
     */
    private static void overrideMethod() {
        System.out.println("start overrideMethod");
        final Parent parent = new Child();
        parent.hello();

        final Child child = new Child();
        child.hello();
    }
}
