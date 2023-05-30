package core.basic.singleton;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

	@Test
	void statefulServiceSingleton() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
		StatefulService bean1 = ac.getBean(StatefulService.class);
		StatefulService bean2 = ac.getBean(StatefulService.class);
		
		int price1 = bean1.order("userA", 10000);
		int price2 = bean1.order("userB", 20000);
		
//		int price1 = bean1.getPrice();
//		int price2 = bean2.getPrice();
		
		System.out.println(price1);
		System.out.println(price2);
		Assertions.assertThat(price1).isEqualTo(10000);
	}
	
	static class TestConfig {
		
		@Bean
		public StatefulService statefulService() {
			return new StatefulService();
		}
	}

}
