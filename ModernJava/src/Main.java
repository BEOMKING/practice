import apple.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple(Color.GREEN, 500));
        apples.add(new Apple(Color.GREEN, 800));
        apples.add(new Apple(Color.RED, 300));

        Apples appless = new Apples(apples);
        appless.prettyPrintApple(new PrettyBigApple());
    }
}
