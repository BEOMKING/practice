package apple;

public class Apple {
    private final Color color;
    private final int weight;

    public Apple(Color color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isGreen() {
        return color.isGreen(color);
    }

    public boolean isSameColor(Color color) {
        return this.color.equals(color);
    }
}
