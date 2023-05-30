package chapter2;

import java.util.ArrayList;
import java.util.List;

public class Apples {
    private final List<Apple> apples;

    public Apples(List<Apple> apples) {
        this.apples = apples;
    }

//    public static List<Apple> filter(List<Apple> apples, ApplePredicate p) {
//        List<Apple> result = new ArrayList<>();
//
//        for (Apple apple : apples) {
//            if (p.test(apple)) {
//                result.add(apple);
//            }
//        }
//
//        return result;
//    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();

        for (T e : list) {
            if (p.test(e)) {
                result.add(e);
            }
        }

        return result;
    }

    public void prettyPrintApple(PrettyPredicate p) {
        for (Apple apple : apples) {
            System.out.println(p.isPretty(apple));
        }
    }

}
