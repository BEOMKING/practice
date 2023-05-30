package chapter3.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Functional {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("BJP");
        list.add("bjp");
        list.add("");
        Supplier<Integer> supplier = () -> 10;
        UnaryOperator<String> unaryOperator = String::toUpperCase;
    }
}
