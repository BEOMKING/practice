package core.basic.scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

	@Test
	void PrototypeBeanFine() {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(PrototypeBean.class);
		System.out.println("bean1");
		PrototypeBean bean = annotationConfigApplicationContext.getBean(PrototypeBean.class);
		System.out.println("bean2");
		PrototypeBean bean2 = annotationConfigApplicationContext.getBean(PrototypeBean.class);
		System.out.println(bean);
		System.out.println(bean2);
		Assertions.assertThat(bean).isNotEqualTo(bean2);
		
		annotationConfigApplicationContext.close();
	}
	
	@Scope("prototype")
	static class PrototypeBean {
		
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
