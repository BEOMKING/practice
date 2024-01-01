package org.example.extends1.ps;

public class ExPsMain {
    public static void main(String[] args) {
        final Book book = new Book("가상 면접으로 알아보는 대규모 시스템 설계 기초", 30000, "조대협", "9791189909178");
        final Album album = new Album("앨범", 20000, "Christoper");
        final Movie movie = new Movie("영화", 10000, "감독", "배우");

        book.print();
        album.print();
        movie.print();

        int sum = book.getPrice() + album.getPrice() + movie.getPrice();
        System.out.println("합계: " + sum);
    }
}
