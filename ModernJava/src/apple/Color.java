package apple;

public enum Color {
    RED, GREEN;

    public boolean isGreen(Color color) {
        return GREEN.equals(color);
    }
}
