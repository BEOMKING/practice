package chapter3;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public class Main {

    public static void main(String[] args) throws IOException {
        String result = ExecuteAroundPattern.processFile((BufferedReader br) ->
                br.readLine() + br.readLine());
        System.out.println(result);

        List<String> list = new ArrayList<>();
        list.add("BJP");
        list.add("bjp");
        list.add("");

        ToIntFunction<String> stringToInt = (Integer::parseInt);
        BiPredicate<List<String>, String> contains = (List::contains);
//        List<String> containsList = Main.filter(list, "b", contains);
//        System.out.println(containsList.size());
    }

    public static <T> List<T> filter(List<T> list, T e, BiPredicate<T, T> tuBiPredicate) {
        List<T> results = new ArrayList<>();

        for (T t : list) {
            if (tuBiPredicate.test(t, e)) {
                results.add(t);
            }
        }

        return results;
    }
}
