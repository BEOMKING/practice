package org.example.extends1.parent;

public class Parent {
    public int publicValue;
    protected int protectedValue;
    int defaultValue;
    private int privateValue;

    public String publicMethod() {
        return "publicMethod";
    }

    protected String protectedMethod() {
        return "protectedMethod";
    }

    String defaultMethod() {
        return "defaultMethod";
    }

    private String privateMethod() {
        return "privateMethod";
    }
}
