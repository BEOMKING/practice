package core.basic.scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonTest {

	@Test
	void singletonBeanFine() {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SingletonBean.class);
		System.out.println("bean1");
		SingletonBean bean = annotationConfigApplicationContext.getBean(SingletonBean.class);
		System.out.println("bean2");
		SingletonBean bean2 = annotationConfigApplicationContext.getBean(SingletonBean.class);
		System.out.println(bean);
		System.out.println(bean2);
		Assertions.assertThat(bean).isEqualTo(bean2);
		
		annotationConfigApplicationContext.close();
	}
	
	@Scope("singleton")
	static class SingletonBean {
		
		@PostConstruct
		public void init() {
			System.out.println("init");
		}
		
		@PreDestroy
		public void destroy() {
			System.out.println("destory");
		}
	}
}
