package org.example.polymorphism1.casting;

public class CastingMain {
    public static void main(String[] args) {
        Item item = new Album("Album", 100, "Artist");
        item.print();

        Album album = (Album) item;
        album.playMusic();

        // 다운 캐스팅 에러가 발생합니다.
        Book book = (Book) item;
        book.readBook();
    }
}
