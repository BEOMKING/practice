package org.example.extends1.child;

import org.example.extends1.parent.Parent;

public class child extends Parent {
    public void call() {
        publicValue = 1;
        protectedValue = 2;
//        defaultValue = 3; // Compile error
//        privateValue = 4; // Compile error

        publicMethod();
        protectedMethod();
//        defaultMethod(); // Compile error
//        privateMethod(); // Compile error
    }
}
