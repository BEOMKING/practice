package org.example.polymorphism1.instanceof1;

public class InstanceOfMain {
    public static void main(String[] args) {
        Parent parent = new Child();
        parent.parentMethod();

        callChildMethod(parent);
    }

    private static void callChildMethod(final Parent parent) {
        // 자바 16 이전에는 아래와 같이 캐스팅을 해야 사용 가능
        if (parent instanceof Child) {
            Child child = (Child) parent;
            child.childMethod();
        }

        // 자바 16 부터는 아래와 같이 캐스팅 없이도 사용 가능
        if (parent instanceof Child child) {
            child.childMethod();
        }
    }
}
