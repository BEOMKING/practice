package chapter6;

import chapter4.Dish;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static chapter4.Dish.menu;

public class Grouping {

    public void grouping() {
        Map<Dish.Type, List<Dish>> dishesType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType));
    }

    enum CaloricLevel { Diet, Normal, Fat }

    public static void customGrouping() {
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream()
                .collect(Collectors.groupingBy(dish -> {
                    if (dish.getCalories() < 400) return CaloricLevel.Diet;
                    else if (dish.getCalories() < 700) return CaloricLevel.Normal;
                    else return CaloricLevel.Fat;
                }));
        System.out.println("dishesByCaloricLevel = " + dishesByCaloricLevel);
    }

    public static void groupingByCalorieBiggerThen500() {
//        Map<Dish.Type, List<Dish>> dishesByType = menu.stream()
//                .collect(Collectors.groupingBy(Dish::getType, Collectors.filtering(dish -> dish.getCalories() > 500, Collectors.toList())));
//        System.out.println(dishesByType);
    }

    public static void groupingByTypeMappingName() {
        Map<Dish.Type, List<String>> dishes = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(Dish::getName, Collectors.toList())));
        System.out.println(dishes);
    }

    public static void main(String[] args) {
        customGrouping();
        groupingByCalorieBiggerThen500();
        groupingByTypeMappingName();
    }
}
