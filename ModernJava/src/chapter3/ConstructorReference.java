package chapter3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ConstructorReference {
    class CustomSupplier implements Supplier<Person> {
        @Override
        public Person get() {
            return new Person();
        }
    }

    public void supplier() {
//        동작 파라미터화
//        Person p = new CustomSupplier().get();

//        익명 클래스
//        Supplier<Person> supplier = new Supplier<Person>() {
//            @Override
//            public Person get() {
//                return new Person();
//            }
//        };

//        람다
//        Supplier<Person> supplier = () -> new Person();

        // 생성자 레퍼런스
        Supplier<Person> supplier = Person::new;
        Person make = supplier.get();
    }

    public void function() {
//        Function<Integer, Person> function = new Function<Integer, Person>() {
//            @Override
//            public Person apply(Integer integer) {
//                return new Person(integer);
//            }
//        }

//        Function<Integer, Person> function = (Integer i) -> new Person(i);

        Function<Integer, Person> function = Person::new;
        Person make = function.apply(100);

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        List<Person> personList = map(integers, function);
        assert personList.size() == 5;
    }

    public void biFunction() {
//        BiFunction<String, Integer, Person> biFunction = new BiFunction<String, Integer, Person>() {
//            @Override
//            public Person apply(String s, Integer integer) {
//                return new Person(s, integer);
//            }
//        };
//        BiFunction<String, Integer, Person> biFunction = (s, i) -> new Person(s, i);
        BiFunction<String, Integer, Person> biFunction = Person::new;
        Person person = biFunction.apply("BJP", 65);
    }

    public List<Person> map(List<Integer> list, Function<Integer, Person> function) {
        List<Person> personList = new ArrayList<>();

        for (Integer i : list) {
            personList.add(function.apply(i));
        }

        return personList;
    }

    interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }

    class Person {
        private String name;
        private int weight;

        public Person(String name, int weight) {
            this.name = name;
            this.weight = weight;
        }

        public Person(int weight) {
            this.weight = weight;
        }

        public Person() {
        }

    }
}
