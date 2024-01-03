package org.realworld;

public class Main {
    public static void main(String[] args) {
        final var env = new Facts();
        final var businessRuleEngine = new BusinessRuleEngine(env);

        businessRuleEngine.addAction(facts -> {
            var dealStage = Stage.valueOf(facts.getFacts("stage"));
            var amount = Double.parseDouble(facts.getFacts("amount"));

            var forecastedAmount = amount * switch (dealStage) {
                case LEAD -> 0.2;
                case EVALUATING -> 0.5;
                case INTERESTED -> 0.8;
                case CLOSED -> 1;
            };

            facts.addFacts("forecastedAmount", String.valueOf(forecastedAmount));
        });

        businessRuleEngine.run();
    }

    public static void main2(String[] args) {
        final Facts facts = new Facts();
        facts.addFacts("name", "Bob");
        facts.addFacts("jobTitle", "CEO");

        Rule rule = RuleBuilder.builder()
                .when(f -> "CEO".equals(f.getFacts("jobTitle")))
                .then(f -> Mailer.sendEmail("email", "wow" + facts.getFacts("name")))
                .build();
    }
}
