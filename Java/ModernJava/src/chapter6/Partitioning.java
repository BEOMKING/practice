package chapter6;

import chapter4.Dish;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static chapter4.Dish.menu;
import static java.util.stream.Collectors.*;

// 분할은 분할 함수라 불리는 프레디케이트를 분류 함수로 사용하는 특수한 그룹화 기능
public class Partitioning {

    static void before() {
        // 참 거짓인 경우를 모두 포함
        Map<Boolean, List<Dish>> collect = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian));
        // 채식주의자 여부로 파티셔닝
        List<Dish> isVegetarian1 = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toList());

        List<Dish> isVegetarian2 = collect.get(true);
        // 채식주의자 여부 + 요리의 타입으로 그룹화
        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        groupingBy(Dish::getType)));
        // 채식 여부 + 가장 고칼로리
        Map<Boolean, Dish> vegetarianMostCalorie = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));

        System.out.println(vegetarianMostCalorie);
    }

    static void partitioningPractice() {
        Map<Boolean, Map<Boolean, List<Dish>>> collect1 = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        partitioningBy(dish -> dish.getCalories() >= 500)));
        System.out.println("collect1 = " + collect1);
        Map<Boolean, Long> collect2 = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        counting()));
        System.out.println("collect2 = " + collect2);
    }

    // 주어진 숫자의 소수 비소수를 판단하는 프레디케이트
    static boolean isPrime(int candidate) {
        // 주어진 수가 소수인지 아닌지
        boolean b1 = IntStream.range(2, candidate)
                .noneMatch(i -> candidate % i == 0);
        // 소수의 대상을 주어진 수의 제곱근 이하로 제한한다.
        int candidateRoot = (int) Math.sqrt(candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }

    static void partitioningByPrime(int n) {
        Map<Boolean, List<Integer>> collect = IntStream.rangeClosed(2, n)
                .boxed()
                .collect(partitioningBy(Partitioning::isPrime));
        System.out.println("collect = " + collect);
    }



    public static void main(String[] args) {
//        before();
//        partitioningPractice();
        partitioningByPrime(20);
    }
}
