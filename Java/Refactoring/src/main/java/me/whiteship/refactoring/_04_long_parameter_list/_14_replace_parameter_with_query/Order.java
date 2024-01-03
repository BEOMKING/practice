package me.whiteship.refactoring._04_long_parameter_list._14_replace_parameter_with_query;

public class Order {

    private int quantity;

    private double itemPrice;

    public Order(int quantity, double itemPrice) {
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public double finalPrice() {
        return this.discountedPrice(calculatePrice());
    }

    private int measureLevel() {
        return this.quantity > 100 ? 2 : 1;
    }

    private double calculatePrice() {
        return this.quantity * this.itemPrice;
    }

    private double discountedPrice(double basePrice) {
        return measureLevel() == 2 ? basePrice * 0.90 : basePrice * 0.95;
    }
}
