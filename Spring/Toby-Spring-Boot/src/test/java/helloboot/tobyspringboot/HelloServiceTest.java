package helloboot.tobyspringboot;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HelloServiceTest {
    @Test
    void simpleHelloServiceTest() {
        HelloService helloService = new SimpleHelloService(new HelloRepository() {
            @Override
            public Hello findHello(final String name) {
                return null;
            }

            @Override
            public void increaseHello(final String name) {

            }
        });

        String ret = helloService.sayHello("Spring");

        assertThat(ret).isEqualTo("Hello, Spring");
    }

    @Test
    void decoratorHelloServiceTest() {
        HelloService helloService = new HelloDecorator(name -> name);

        String ret = helloService.sayHello("Spring");

        assertThat(ret).isEqualTo("*Spring*");
    }
}
