package org.realworld;

public class RuleBuilder {
    private Condition condition;
    private Action action;

    private RuleBuilder ruleBuilder() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static RuleBuilder builder() {
        return new RuleBuilder();
    }

    public RuleBuilder when(final Condition condition) {
        this.condition = condition;
        return this;
    }

    public RuleBuilder then(final Action action) {
        this.action = action;
        return this;
    }

    public Rule build() {
        return new DefaultRule(condition, action);
    }
}
