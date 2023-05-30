package core.basic.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import core.basic.AppConfig;
import core.basic.member.MemberRepository;
import core.basic.member.MemberService;
import core.basic.member.MemberServiceImpl;
import core.basic.order.OrderService;
import core.basic.order.OrderServiceImpl;

public class ConfigurationSingletonTest {

	@Test
	void configurationTest( ) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		
		MemberServiceImpl memberService = ac.getBean(MemberServiceImpl.class);
		OrderServiceImpl orderService = ac.getBean(OrderServiceImpl.class);
		MemberRepository bean = ac.getBean(MemberRepository.class);
		
		MemberRepository memberRepository1 = memberService.getMemberRepository();
		MemberRepository memberRepository2 = orderService.getMemberRepository();
		
		System.out.println(memberRepository1);
		System.out.println(memberRepository2);
		System.out.println(bean);
		
		Assertions.assertThat(memberRepository1).isEqualTo(memberRepository2);
	}
	
	@Test
	void configurationDeep() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		
		AppConfig bean = ac.getBean(AppConfig.class);
		
		System.out.println(bean.getClass());
	}
}
