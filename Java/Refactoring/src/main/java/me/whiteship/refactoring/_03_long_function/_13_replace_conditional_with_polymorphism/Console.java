package me.whiteship.refactoring._03_long_function._13_replace_conditional_with_polymorphism;

import java.io.IOException;
import java.util.List;

public class Console implements Printer {
    private final int totalNumberOfEvents;
    private final List<Participant> participants;

    public Console(final int totalNumberOfEvents, final List<Participant> participants) {
        this.totalNumberOfEvents = totalNumberOfEvents;
        this.participants = participants;
    }

    @Override
    public void execute() throws IOException {
        this.participants.forEach(p -> {
            System.out.printf("%s %s:%s\n", p.username(), checkMark(p), p.getRate(this.totalNumberOfEvents));
        });
    }

    /**
     * |:white_check_mark:|:white_check_mark:|:white_check_mark:|:x:|
     */
    private String checkMark(Participant p) {
        StringBuilder line = new StringBuilder();
        for (int i = 1 ; i <= this.totalNumberOfEvents ; i++) {
            if(p.homework().containsKey(i) && p.homework().get(i)) {
                line.append("|:white_check_mark:");
            } else {
                line.append("|:x:");
            }
        }
        return line.toString();
    }
}
