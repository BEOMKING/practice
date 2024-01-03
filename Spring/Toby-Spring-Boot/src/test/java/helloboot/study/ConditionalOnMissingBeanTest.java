package helloboot.study;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class ConditionalOnMissingBeanTest {
    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ConditionalOnMissingBeanTest.ExampleConfiguration.class);
        context.register(ConditionalOnMissingBeanTest.ExampleConfiguration2.class);
        context.refresh();

        ExampleConfiguration bean = context.getBean(ExampleConfiguration.class);
        ExampleConfiguration2 bean2 = context.getBean(ExampleConfiguration2.class);

        assertThat(bean.ec).isNull();
        assertThat(bean2.ec).isNotNull();
    }

    @Configuration
    static class ExampleConfiguration {
        ExampleClass ec;
        @Bean
        @ConditionalOnMissingBean
        public ExampleClass exampleClass() {
            FirstClass firstClass = new FirstClass();
            ec = firstClass;
            return firstClass;
        }
    }

    @Configuration
    static class ExampleConfiguration2 {
        ExampleClass ec;
        @Bean
        public ExampleClass exampleClass() {
            SecondClass secondClass = new SecondClass();
            ec = secondClass;
            return secondClass;
        }
    }

    interface ExampleClass {
    }

    static class FirstClass implements ExampleClass {
    }

    static class SecondClass implements ExampleClass {
    }
}
