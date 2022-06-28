package chapter6;

import chapter4.Dish;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static chapter4.Dish.specialMenu;
import static chapter4.Dish.menu;

public class Reducing {

    public void count() {
        long howManyDishes = menu.stream().collect(Collectors.counting());
        long howManyDishes2 = menu.stream().count();
        long howManyDishes3 = menu.size();
    }

    public void minMax() {
        Comparator<Dish> dishComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCalorieDish = menu.stream()
                .collect(Collectors.maxBy(dishComparator));
        Optional<Dish> mostCalorieDish2 = menu.stream().max(dishComparator);
    }

    public void totalCalories() {
        int totalCalores = menu.stream()
                .collect(Collectors.summingInt(Dish::getCalories));
        int totalCalores2 = menu.stream().mapToInt(Dish::getCalories).sum();
    }

    public void average() {
        double average = menu.stream()
                .collect(Collectors.averagingInt(Dish::getCalories));
    }

    public static void summary() {
        IntSummaryStatistics summary = menu.stream()
                .collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println(summary);
    }

    public static void joining() {
        String shortMenu = menu.stream()
                .map(Dish::getName)
                .collect(Collectors.joining(", "));
        System.out.println("shortMenu = " + shortMenu);;
    }

    public static void reducing() {
        int totalCalories = menu.stream()
                .map(Dish::getCalories)
                .reduce(Integer::sum)
                .get();

        int totalCalories2 = menu.stream()
                .collect(Collectors
                        .reducing(0, Dish::getCalories, (i, j) -> i + j));

        int totalCalories3 = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, (i, j) -> i + j);

        Optional<Dish> maxCalorieDish = menu.stream()
                .collect(Collectors.reducing(
                        (d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));

        Optional<Dish> maxCalorieDish2 = menu.stream()
                .reduce((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2);
    }

    public void differenceCollectAndReduce() {
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();

        List<Integer> numbers = stream
                .reduce(new ArrayList<>(),
                        (List<Integer> l, Integer e) -> {
                                                            l.add(e);
                                                            return l;
                                                        },
                        (List<Integer> l1, List<Integer> l2) -> {
                                                            l1.addAll(l2);
                                                            return l1;
                                                        });
    }

    public static void main(String[] args) {
        summary();
        joining();
    }
}
