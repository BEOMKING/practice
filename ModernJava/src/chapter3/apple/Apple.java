package chapter3.apple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Apple {
    private int weight;

    public Apple(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}

class Sort {

    public static void comparingWeight() {
        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple(300));
        apples.add(new Apple(200));
        apples.add(new Apple(100));

        apples.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });

        apples.sort(((o1, o2) -> o1.getWeight() - o2.getWeight()));

        apples.sort(Comparator.comparing(Apple::getWeight));

        for (Apple a : apples) {
            System.out.println(a.getWeight());
        }
    }

    public static void main(String[] args) {
        Sort.comparingWeight();
    }
}