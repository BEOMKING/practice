package chapter5;

import chapter4.Dish;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static chapter4.Dish.menu;

public class Primitive {

    public static void mapToInt() {
        int calories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();

        IntStream inputStream = menu.stream()
                .mapToInt(Dish::getCalories);
        Stream<Integer> stream = inputStream.boxed();

        OptionalInt max = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();

    }

    public static void pythagoras() {

    }

    public static void streamOf() {
        Stream<String> stream = Stream.of("Park", "Beom", "Jin");
        stream.map(String::toUpperCase)
                .forEach(System.out::println);
    }

    public static void fileWords() {
        long uniqueWords = 0;
        try(Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
        } catch (IOException e) {

        }
    }

    public static void fibonacci() {
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .forEach(t -> System.out.println(t[0] + " " + t[1]));

    }

    public static void main(String[] args) {
        streamOf();
    }
}
