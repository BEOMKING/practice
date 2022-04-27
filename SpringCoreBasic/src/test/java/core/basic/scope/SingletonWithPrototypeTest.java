package core.basic.scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeTest {

	@Test
	void prototypeFind() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
		PrototypeBean bean = ac.getBean(PrototypeBean.class);
		bean.addCount();
		Assertions.assertThat(bean.getCount()).isEqualTo(1);
		
		PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
		bean2.addCount();
		Assertions.assertThat(bean.getCount()).isEqualTo(1);
	}

	@Test
	void singletonClientUsePrototype() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
			PrototypeBean.class, ClientBean.class);

		ClientBean bean = ac.getBean(ClientBean.class);
		int count1 = bean.logic();
		Assertions.assertThat(count1).isEqualTo(1);

		ClientBean bean2 = ac.getBean(ClientBean.class);
		int count2 = bean2.logic();
		Assertions.assertThat(count2).isEqualTo(1);
	}

	@Scope
	static class ClientBean {

		private final ObjectProvider<PrototypeBean> prototypeBeanObjectProvider;

		@Autowired
		public ClientBean(
			 ObjectProvider<PrototypeBean> prototypeBeanObjectProvider) {
			this.prototypeBeanObjectProvider = prototypeBeanObjectProvider;
		}

		public int logic() {
			PrototypeBean prototypeBean = prototypeBeanObjectProvider.getObject();
			prototypeBean.addCount();
			return prototypeBean.getCount();
		}
	}

	@Scope("prototype")
	static class PrototypeBean {
		private int count = 0;
		
		public void addCount() {
			count++;
		}
		
		public int getCount() {
			return count;
		}
		
		@PostConstruct
		public void init() {
			System.out.println("init" + this);
		}
		
		@PreDestroy
		public void destory( ) {
			System.out.println("destory" + this);
		}
	}
}
