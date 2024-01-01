package org.example.polymorphism1.casting;

public class Album extends Item {
    private final String artist;

    public Album(final String name, final int price, final String artist) {
        super(name, price);
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void print() {
        super.print();
        System.out.println("- 아티스트: " + artist);
    }

    public void playMusic() {
        System.out.println(this.artist + "의 음악을 재생합니다.");
    }
}
