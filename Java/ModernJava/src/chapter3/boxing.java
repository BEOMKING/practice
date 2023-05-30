package chapter3;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class boxing {

    public static void main(String[] args) {
        IntPredicate even = (int i) -> i % 2 == 0;
        System.out.println(even.test(1000));
        Predicate<Integer> odd = (Integer i) -> i % 2 == 0;
        System.out.println(odd.test(1000));
    }
}
