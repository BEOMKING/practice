package helloboot.tobyspringboot;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class HelloControllerTest {
    @Test
    void helloControllerTest() {
        HelloController helloController = new HelloController(name -> name);

        String ret = helloController.hello("Hello");

        assertEquals(ret, "Hello");
    }

    @Test
    void helloControllerNullTest() {
        HelloController helloController = new HelloController(name -> name);

        assertThatThrownBy(() -> helloController.hello(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> helloController.hello(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
