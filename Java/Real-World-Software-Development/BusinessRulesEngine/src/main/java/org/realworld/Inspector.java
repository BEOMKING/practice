package org.realworld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inspector {
    private final List<ConditionalAction> conditionalActions;

    public Inspector(final ConditionalAction... conditionalActions) {
        this.conditionalActions = Arrays.asList(conditionalActions);
    }

    public List<Report> inspect(final Facts facts) {
        final List<Report> reports = new ArrayList<>();

        for (final ConditionalAction conditionalAction : conditionalActions) {
            final boolean isPositive = conditionalAction.evaluate(facts);
            final Report report = new Report(conditionalAction, facts, isPositive);
            reports.add(report);
        }

        return reports;
    }
}
