package chapter5;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static chapter4.Dish.menu;

public class Reduce {

    static final List<Integer> list = Arrays.asList(321, 23, 453, 6, 31);

    public static void sum() {
        System.out.println(list.stream()
                .reduce(0, (a, b) -> a + b));

        Optional<Integer> sum = list.stream()
                .reduce(Integer::sum);
    }

    public static void multiplication() {
        System.out.println(list.stream()
                .reduce(1, (a, b) -> a * b));
    }

    public static void maxMin() {
        int max = list.stream()
                .reduce(Integer.MIN_VALUE, Integer::max);
        int min = list.stream()
                .reduce(Integer.MAX_VALUE, Integer::min);
        System.out.println("max = " + max);
        System.out.println("min = " + min);
        Optional<Integer> optionalMax = list.stream()
                .reduce(Integer::max);
        Optional<Integer> optionalMin= list.stream()
                .reduce(Integer::min);
    }

    public static void menuCount() {
        System.out.println(menu.stream().count());
        System.out.println((long) menu.size());
        System.out.println(menu.stream()
                .map(d -> 1)
                .reduce(Integer::sum));
        System.out.println(menu.parallelStream()
                .map(d -> 1)
                .reduce(Integer::sum));
    }

    public static void main(String[] args) {
        sum();
        multiplication();
        maxMin();
        menuCount();
    }
}
