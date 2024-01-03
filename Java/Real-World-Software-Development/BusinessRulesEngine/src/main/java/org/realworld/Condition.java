package org.realworld;

@FunctionalInterface
public interface Condition {
    boolean evaluate(Facts facts);
}
