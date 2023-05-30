package chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PredicateExample {

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();

        for (T t : list) {
            if (p.test(t)) {
                results.add(t);
            }
        }

        return results;
    }

    public static void main(String[] args) {
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        List<String> stringPredicate = new ArrayList<>();
        stringPredicate.add("BJP");
        stringPredicate.add("");
        stringPredicate.add("PBJ");
        System.out.println(PredicateExample.filter(stringPredicate, nonEmptyStringPredicate).size() == 2);
    }
}
