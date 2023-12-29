package org.example.extends1.ps;

public class Item {
    private String name;
    private int price;

    public Item(final String name, final int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    protected void print() {
        System.out.println("상품명: " + name + ", 가격: " + price);
    }
}
