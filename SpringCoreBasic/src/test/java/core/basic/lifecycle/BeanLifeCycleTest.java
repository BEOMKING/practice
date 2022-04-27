package core.basic.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class BeanLifeCycleTest {

	@Test
	void test() {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
		NetworkClient client = annotationConfigApplicationContext.getBean(NetworkClient.class);
		annotationConfigApplicationContext .close();
	}
	
	@Configuration
	static class LifeCycleConfig {
		
//		@Bean(initMethod = "init", destroyMethod = "close")
		@Bean
		public NetworkClient networkClient() {
			NetworkClient networkClient = new NetworkClient();
			networkClient.setUrl("http://naver.com");
			return networkClient;
		}
	}

}
