package apple;

public class PrettyBigApple implements PrettyPredicate {
    @Override
    public String isPretty(Apple apple) {
        if (apple.getWeight() > 500) {
            return apple.getWeight() + "Pretty";
        }
        return apple.getWeight() + "Not Pretty";
    }
}
