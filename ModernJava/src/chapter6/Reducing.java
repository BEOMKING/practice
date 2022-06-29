package chapter6;

import chapter4.Dish;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        int totalCalories3 = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, (i, j) -> i + j);

        int totalCalories2 = menu.stream()
                .collect(Collectors
                        .reducing(0, Dish::getCalories, (i, j) -> i + j));

        int totalCalories4 = menu.stream()
                .collect(Collectors
                        .reducing(0, Dish::getCalories, Integer::sum));

        Optional<Dish> maxCalorieDish = menu.stream()
                .collect(Collectors.reducing(
                        (d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));

        Optional<Dish> maxCalorieDish2 = menu.stream()
                .reduce((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2);
    }

    public void differenceCollectAndReduce() {
        // 잘못된 예제
        // collect 메서드는 도출하려는 결과를 누적하는 컨테이너를 바꾸도록 설계된 메서드
        // reduce는 두 값을 하나로 도출하는 불변형 연산
        // 아래 예제에서 reduce는 누적자로 사용된 리스트를 변환
        // 가변 컨테이너 관련 잡업에서 병렬성을 확보하려면 collect 메서드로 리듀싱을 해야 한다. -> 7장
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);

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
    // 스트림 메서드를 사용하는 것보다 컬렉터가 더 복잡하다.
    // 대신 재사용성과 커스텀 가능성을 제공하는 높은 수준의 추상화와 일반화를 얻을 수 있다.
    public void quiz() {
        String shortMenu = menu.stream()
                .map(Dish::getName)
                .collect(Collectors.joining());

        String shortMenu1 = menu.stream()
                .map(Dish::getName)
                .collect(Collectors.reducing((n1, n2) -> n1 + n2)).get();

        String shortMenu2 = menu.stream()
                .collect(Collectors.reducing("", Dish::getName, (d1, d2) -> d1 + d2));

    }

    public static void main(String[] args) {
        summary();
        joining();
    }
}
