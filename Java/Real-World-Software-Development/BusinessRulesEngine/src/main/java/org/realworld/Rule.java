package org.realworld;

@FunctionalInterface
public interface Rule {
    void perform(Facts facts);
}
