package helloboot.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationTest {
    @Test
    void configuration() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);

        Bean1 bean1 = ac.getBean(Bean1.class);
        Bean2 bean2 = ac.getBean(Bean2.class);

        Assertions.assertThat(bean1.common).isNotSameAs(bean2.common);
    }

    @Test
    void proxyCommonMethod() {
        ConfigProxy configProxy = new ConfigProxy();

        Bean1 bean1 = configProxy.bean1();
        Bean2 bean2 = configProxy.bean2();

        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    static class ConfigProxy extends Config {
        private Common common;

        @Override
        Common common() {
            if (this.common == null) {
                this.common = super.common();
            }

            return this.common;
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class Config {
        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }
    }

    static class Bean1 {
        private final Common common;

        Bean1(final Common common) {
            this.common = common;
        }
    }

    static class Bean2 {
        private final Common common;

        Bean2(final Common common) {
            this.common = common;
        }
    }

    static class Common {
    }
}
