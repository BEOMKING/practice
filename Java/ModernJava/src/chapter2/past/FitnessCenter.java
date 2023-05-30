package chapter2.past;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FitnessCenter {

    private final String name;
    private final int annualPrice;
    private final Status status;

    enum Status {
        GOOD, FINE, BAD
    }

    public FitnessCenter(String name, int annualPrice, Status status) {
        this.name = name;
        this.annualPrice = annualPrice;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getAnnualPrice() {
        return annualPrice;
    }

    public Status getStatus() {
        return status;
    }

    public static <T> List<T> isGood(List<T> list, Predicate<T> p) {
        List<T> goodCenters = new ArrayList<>();

        for (T t : list) {
            if (p.test(t)) {
                goodCenters.add(t);
            }
        }

        return goodCenters;
    }

    public static void main(String[] args) throws IOException {
        List<FitnessCenter> list = Arrays.asList(new FitnessCenter("BJP", 10, Status.BAD));

        List<FitnessCenter> statusGoodCenter = FitnessCenter.isGood(list,
                (FitnessCenter f) -> f.getStatus().equals(Status.GOOD));
    }
}

interface Predicate<T> {
    boolean test(T t);
}

