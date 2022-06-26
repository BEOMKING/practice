package chapter5;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static chapter5.Dish.specialMenu;

public class Filtering {

    public static void isEvenAndDistinct() {
        List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 4, 5, 6, 7, 7, 8, 8, 9, 10);
        List<Integer> evenAndDistinct = numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .collect(Collectors.toList());
        assert evenAndDistinct.size() == 5;
    }

    public static void takeWhile() {
        // 조건을 충족하지 않을 때까지 가져가고 나머지를 버림
        List<Dish> takeWhileMenus = specialMenu.stream()
                .takeWhile(d -> d.getCalories() < 320)
                .collect(Collectors.toList());
    }

    public static void dropWhile() {
        // 조건을 충족하지 않을 때까지 버리고 나머지를 가져감
        List<Dish> dropWhileMenus = specialMenu.stream()
                .dropWhile(d -> d.getCalories() < 320)
                .collect(Collectors.toList());
    }

    public static void limit() {
        specialMenu.stream()
                .filter(d -> d.getCalories() >= 300)
                .limit(3)
                .forEach(System.out::println);
    }

    public static void skip() {
        specialMenu.stream()
                .filter(d -> d.getCalories() >= 300)
                .skip(2)
                .forEach(System.out::println);
    }

    public static void firstTwoMeatDish() {
        List<Dish> firstTwoDish = specialMenu.stream()
                .filter(d -> d.getType().equals(Dish.Type.MEAT))
                .limit(2)
                .collect(Collectors.toList());
        specialMenu.stream()
                .filter(d -> d.getType().equals(Dish.Type.MEAT))
                .limit(2)
                .forEach(System.out::println);
    }

    public static void mappingName() {
        List<String> dishesNames = specialMenu.stream()
                .map(Dish::getName)
                .collect(Collectors.toList());
    }

    public static void distinctAlphabet() {
        List<String> characters = specialMenu.stream()
                .map(Dish::getName)
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(characters);
    }

    public static void exponentiation() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> exponentiation = integers.stream()
                .map(i -> i * i)
                .collect(Collectors.toList());
        exponentiation.forEach(System.out::println);

        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(3, 4);
        List<int[]> pairs = list1.stream()
                .flatMap(i -> list2.stream()
                        .map(j -> new int[]{i, j}))
                .collect(Collectors.toList());

        for (int[] pair : pairs) {
            System.out.println(pair[0] + " " + pair[1]);
        }
        System.out.println("--------------");

        List<int[]> division3Pairs = list1.stream()
                .flatMap(i -> list2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i, j}))
                .collect(Collectors.toList());

        for (int[] division3Pair : division3Pairs) {
            System.out.println(division3Pair[0] + " " + division3Pair[1]);
        }

    }

    public static void main(String[] args) {
//        isEvenAndDistinct();
//        limit();
//        skip();
//        firstTwoMeatDish();
//        distinctAlphabet();
        exponentiation();
    }

}
