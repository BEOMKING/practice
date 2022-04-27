package core.basic.autowired;

import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import core.basic.AutoAppConfig;
import core.basic.discount.DiscountPolicy;
import core.basic.member.Grade;
import core.basic.member.Member;

public class AllBeanTest {

	@Test
	void findAllBean() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
		DiscountService bean = ac.getBean(DiscountService.class);
		Member member = new Member(1L, "userA", Grade.VIP);
		int discountPrice = bean.discount(member, 10000, "fixDiscountPolicy");
		int discountPrice2 = bean.discount(member, 20000, "rateDiscountPolicy");
		
		Assertions.assertThat(bean).isInstanceOf(DiscountService.class);
		Assertions.assertThat(discountPrice).isEqualTo(1000);
		Assertions.assertThat(discountPrice2).isEqualTo(2000);
	}
	
	static class DiscountService {
		private final Map<String, DiscountPolicy> policyMap;
		private final List<DiscountPolicy> policies;
		
		@Autowired
		public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
			this.policyMap = policyMap;
			this.policies = policies;
			System.out.println(policyMap);
			System.out.println(policies);
		}

		public int discount(Member member, int i, String string) {
			DiscountPolicy discountPolicy = policyMap.get(string);
			return discountPolicy.discount(member, i);
		}
		
		
	}
}
