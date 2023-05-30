package chapter5;

import chapter4.Dish;

import java.util.Arrays;
import java.util.List;

import static chapter4.Dish.menu;

public class SearchingAndMatching {

    public static void anyMatch() {
        System.out.println(menu.stream().anyMatch(Dish::isVegetarian));
    }

    public static void allMatch() {
        System.out.println(menu.stream().allMatch(d -> d.getCalories() < 1000));
    }

    public static void findAny() {
        menu.stream()
            .filter(Dish::isVegetarian)
            .findAny()
            .ifPresent(d -> System.out.println(d.getCalories()));
    }

    public static void findFirst() {
        List<Integer> list = Arrays.asList(9, 24, 3, 445, 22);
        list.stream()
            .filter(i -> i % 3 == 0)
            .map(i -> i * i)
            .findFirst()
            .ifPresent(System.out::println);
    }

    public static void main(String[] args) {
        anyMatch();
        allMatch();
        findAny();
        findFirst();
    }
}
