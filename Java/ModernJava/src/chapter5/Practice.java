package chapter5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Practice {

    static Trader raoul = new Trader("Raoul", "Cambridge");
    static Trader mario = new Trader("Mario", "Milan");
    static Trader alan = new Trader("Alan", "Cambridge");
    static Trader brian = new Trader("Brian", "Cambridge");

    static List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    public static void main(String... args) {
        sortedValueTransactionBy2011();
        cityWhereTradersWork();
        sortedByNameTradersWorkingInCambridge();
        sortedAlphabetTraders();
        hasTraderInMilano();
        sumValueTradersLiveInCambridge();
        maxTransaction();
        minTransaction();
    }

    public static void sortedValueTransactionBy2011() {
        transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .forEach(System.out::println);
    }

    public static void cityWhereTradersWork() {
        transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .forEach(System.out::println);
    }

    public static void sortedByNameTradersWorkingInCambridge() {
        transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .sorted(Comparator.comparing(Trader::getName))
                .distinct()
                .forEach(System.out::println);
    }

    public static void sortedAlphabetTraders() {
        transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .map(s -> s.split(""))
                .flatMap(Arrays::stream)
                .sorted()
                .distinct()
                .forEach(System.out::print);
        System.out.println();

        System.out.println(transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2));
    }

    public static void hasTraderInMilano() {
        transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Milan"))
                .findAny()
                .ifPresent(System.out::println);

        transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("Milan"));
    }

    public static void sumValueTradersLiveInCambridge() {
        transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(Integer::sum)
                .ifPresent(System.out::println);
    }

    public static void maxTransaction() {
        transactions.stream()
                .map(Transaction::getValue)
                .max(Comparator.naturalOrder())
                .ifPresent(System.out::println);

        transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max)
                .ifPresent(System.out::println);
    }

    public static void minTransaction() {
        transactions.stream()
                .map(Transaction::getValue)
                .min(Comparator.naturalOrder())
                .ifPresent(System.out::println);

        transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min)
                .ifPresent(System.out::println);
    }

}
