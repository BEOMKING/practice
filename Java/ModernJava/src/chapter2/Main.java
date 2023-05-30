package chapter2;

import chapter2.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple(Color.GREEN, 500));
        apples.add(new Apple(Color.GREEN, 800));
        apples.add(new Apple(Color.RED, 300));

        Apples appless = new Apples(apples);
        appless.prettyPrintApple(new PrettyBigApple());

        List<Apple> greenApples = Apples.filter(apples, apple -> { return apple.isGreen(); });

        Runnable runnable = () -> System.out.println("df");
    }
}
