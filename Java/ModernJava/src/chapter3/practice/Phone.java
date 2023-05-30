package chapter3.practice;

import java.util.ArrayList;
import java.util.List;

public class Phone {
    private int price;
    private int weight;

    public int getPrice() {
        return price;
    }

    public int getWeight() {
        return weight;
    }

    public Phone(int price, int weight) {
        this.price = price;
        this.weight = weight;
    }

    public static List<Phone> overPricePhones(List<Phone> phones, int price) {
        List<Phone> overPricePhones = new ArrayList<>();

        for (Phone phone : phones) {
            if (phone.price > price) {
                overPricePhones.add(phone);
            }
        }

        return overPricePhones;
    }

    public static List<Phone> overPricePhones(List<Phone> phones, Predicate p) {
        List<Phone> overPricePhones = new ArrayList<>();

        for (Phone phone : phones) {
            if (p.condition(phone)) {
                overPricePhones.add(phone);
            }
        }

        return overPricePhones;
    }

    public interface Predicate {
        boolean condition(Phone phone);
    }

    public static void main(String[] args) {
        List<Phone> phones = new ArrayList<>();
        phones.add(new Phone(10000, 100));
        phones.add(new Phone(5000, 300));
        List<Phone> overPricePhones = Phone.overPricePhones(phones, 7000);
        System.out.println(overPricePhones.size() == 1);

        List<Phone> underPricePhones = Phone.overPricePhones(phones, phone -> phone.getPrice() < 5000);
        List<Phone> overPriceAndUnderWeightPhones = Phone.
                overPricePhones(phones, phone -> phone.getPrice() > 5000 && phone.getWeight() < 500);
        System.out.println(underPricePhones.size() == 0);
    }
}
