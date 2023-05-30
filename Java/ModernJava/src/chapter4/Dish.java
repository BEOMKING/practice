package chapter4;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class Dish {

    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        MEAT,
        FISH,
        OTHER
    }

    @Override
    public String toString() {
        return name;
    }

    public static final List<Dish> menu = asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 400, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH)
    );

    public static List<Dish> specialMenu = asList(
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER));

    public static final Map<String, List<String>> dishTags = new HashMap<>();
        static {
            dishTags.put("pork", asList("greasy", "salty"));
            dishTags.put("beef", asList("salty", "roasted"));
            dishTags.put("chicken", asList("fried", "crisp"));
            dishTags.put("french fries", asList("greasy", "fried"));
            dishTags.put("rice", asList("light", "natural"));
            dishTags.put("season fruit", asList("fresh", "natural"));
            dishTags.put("pizza", asList("tasty", "salty"));
            dishTags.put("prawns", asList("tasty", "roasted"));
            dishTags.put("salmon", asList("delicious", "fresh"));
    }

    public List<String> internalIteration() {
        return menu.stream()
                .filter(d -> d.getCalories() > 300)
                .map(Dish::getName)
                .collect(Collectors.toList());
    }

    public static void forEach() {
//        menu.stream().forEach(dish -> { System.out.println(dish.getName()); });
        menu.forEach(dish -> System.out.println(dish.getName()));
//        menu.forEach(System.out::println);
    }
    public static void threeHighCaloricDishNames() {
        List<String> threeHighCaloricDishNames = menu.stream()
                .sorted(Comparator.comparing(Dish::getCalories).reversed())
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());

        System.out.println(threeHighCaloricDishNames);
    }

    public static void streamNotRepeatable() {
        List<String> list = asList("박", "범", "진");
        Stream<String> streams = list.stream();
        streams.forEach(System.out::print);
        streams.forEach(System.out::print);
    }

    public static void main(String[] args) {
        threeHighCaloricDishNames();
        streamNotRepeatable();
    }

}
