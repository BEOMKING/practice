package chapter5;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Reduce {

    static final List<Integer> list = Arrays.asList(321, 23, 453, 6, 31);

    public static void sum() {
        System.out.println(list.stream()
                .reduce(0, (a, b) -> a + b));
    }

    public static void multiplication() {
        System.out.println(list.stream()
                .reduce(1, (a, b) -> a * b));
    }

    public static void main(String[] args) {
        sum();
        multiplication();
    }
}
