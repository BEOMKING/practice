package org.realworld;

import java.util.ArrayList;
import java.util.List;

public class BusinessRuleEngine {
    private final List<Action> actions;
    private final Facts facts;

    public BusinessRuleEngine(final Facts facts) {
        this.actions = new ArrayList<>();
        this.facts = facts;
    }

    public void addAction(final Action action) {
        actions.add(action);
    }

    public int count() {
        return this.actions.size();
    }

    public void run() {
        this.actions.forEach(action -> action.perform(facts));
    }
}
