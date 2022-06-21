import apple.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Apple> inventory = new ArrayList<>();
        inventory.add(new Apple(Color.GREEN, 500));
        inventory.add(new Apple(Color.GREEN, 800));
        inventory.add(new Apple(Color.RED, 300));

        List<Apple> redApples = Apples.filter(inventory, (Apple a) -> a.getColor().equals(Color.RED));
        inventory.sort(Comparator.comparingInt(Apple::getWeight));

        for (Apple a : inventory) {
            System.out.println(a.getWeight());
        }
    }
}
