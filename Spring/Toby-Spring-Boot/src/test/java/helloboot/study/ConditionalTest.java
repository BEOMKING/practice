package helloboot.study;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ConditionalTest {
    @Test
    void conditional() {
        new ApplicationContextRunner().withUserConfiguration(Config1.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(Config1.class);
                    assertThat(context).hasSingleBean(MyBean.class);
                });

        new ApplicationContextRunner().withUserConfiguration(Config2.class)
                .run(context -> {
                    assertThat(context).doesNotHaveBean(Config2.class);
                    assertThat(context).doesNotHaveBean(MyBean.class);
                });
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(BooleanConditional.BooleanCondition.class)
    @interface BooleanConditional {
        boolean value();

        class BooleanCondition implements Condition {
            @Override
            public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
                Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
                return (Boolean) annotationAttributes.get("value");
            }
        }
    }

    @Configuration
    @BooleanConditional(true)
    static class Config1 {
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }

    @Configuration
    @BooleanConditional(false)
    static class Config2 {
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }

    static class MyBean {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(TrueConditional.TrueCondition.class)
    @interface TrueConditional {
        class TrueCondition implements Condition {
            @Override
            public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
                return true;
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(FalseConditional.FalseCondition.class)
    @interface FalseConditional {
        class FalseCondition implements Condition {
            @Override
            public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
                return false;
            }
        }
    }
}
