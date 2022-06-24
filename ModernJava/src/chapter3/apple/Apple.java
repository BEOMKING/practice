package chapter3.apple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Apple {
    private int weight;
    private int price;

    public Apple(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getPrice() {
        return price;
    }
}

class Letter {
    public static String addHeader(String text) {
        return "Author" + text;
    }

    public static String addFooter(String text) {
        return "Thanks" + text;
    }

    public static String checkSpelling(String text) {
        return text.replaceAll("everytwo", "everyone");
    }
}

class Lambda {

    public static void comparingWeight() {
        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple(300));
        apples.add(new Apple(200));
        apples.add(new Apple(100));
        // 익명 클래스
        apples.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });
        // 람다
        apples.sort(((o1, o2) -> o1.getWeight() - o2.getWeight()));
        // 메서드 참조
        apples.sort(Comparator.comparing(Apple::getWeight));
        // 역정렬
        apples.sort(Comparator.comparing(Apple::getWeight).reversed());
        // Comparator 연결
        apples.sort(Comparator.comparing(Apple::getWeight)
                .reversed()
                .thenComparing(Apple::getPrice)
        );

        for (Apple a : apples) {
            System.out.println(a.getWeight());
        }
    }

    public static void predicateCombination() {
        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple(300));
        apples.add(new Apple(200));
        apples.add(new Apple(40));

        // 몸무게 > 50 Predicate
        Predicate<Apple> overWeight50 = (apple -> apple.getWeight() > 50);
        // 몸무게 < 50 .negate
        Predicate<Apple> underWight50 = overWeight50.negate();

        Predicate<Apple> underWight50AndOverPrice100OrUnderPrice50 = overWeight50
                .negate()
                .and(a -> a.getPrice() > 100)
                .or(a -> a.getPrice() < 50);
        System.out.println(filter(apples, underWight50).size());
    }

    public static void functionCombination() {
        Function<Integer, Integer> f = i -> i * i;
        Function<Integer, Integer> f2 = i -> i - 1;
        Function<Integer, Integer> f3 = f.andThen(f2);
        System.out.println(f3.apply(3));
        Function<Integer, Integer> f4 = f2.andThen(f);
        System.out.println(f4.apply(3));
        Function<Integer, Integer> f5 = f.compose(f2);
        System.out.println(f5.apply(3));

        Function<String, String> lf = Letter::addHeader;
        Function<String, String> lf2 = lf
                .andThen(Letter::addFooter)
                .andThen(Letter::checkSpelling);
        System.out.println(lf2.apply("everytwo"));
    }

    public static List<Apple> filter(List<Apple> apples, Predicate<Apple> p) {
        List<Apple> filteredApples = new ArrayList<>();

        for (Apple apple : apples) {
            if (p.test(apple)) {
                filteredApples.add(apple);
            }
        }

        return filteredApples;
    }

    public static void main(String[] args) {
        Lambda.comparingWeight();
        Lambda.predicateCombination();
        Lambda.functionCombination();
    }
}
