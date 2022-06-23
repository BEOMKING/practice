package chapter3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class FunctionExample {

    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();

        for (T t : list) {
            result.add(f.apply(t));
        }

        return result;
    }

    public static void main(String[] args) {
        List<String> sample = Arrays.asList("lambdas", "in", "action");
        List<Integer> list = map(sample, (String s) -> s.length());
    }
}
