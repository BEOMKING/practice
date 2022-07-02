package chapter6;

import chapter4.Dish;

import java.util.List;
import java.util.stream.Collectors;

import static chapter4.Dish.menu;

public class CustomCollector {

    public void difference() {
        List<Dish> collect = menu.stream()
                .collect(Collectors.toList());

        List<Dish> collect1 = menu.stream()
                .collect(new ToListCollector<Dish>());

    }
}
