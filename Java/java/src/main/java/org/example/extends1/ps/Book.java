package org.example.extends1.ps;

public class Book extends Item {
    private final String author;
    private final String isbn;

    public Book(final String name, final int price, final String author, final String isbn) {
        super(name, price);
        this.author = author;
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void print() {
        super.print();
        System.out.println("- 저자: " + author + ", ISBN: " + isbn);
    }
}
