package core.basic.discount;

import org.springframework.stereotype.Component;

import core.basic.member.Grade;
import core.basic.member.Member;


@Component
public class FixDiscountPolicy implements DiscountPolicy {


	private int discountFixAmount = 1000;
	
	@Override
	public int discount(Member member, int price) {
		if (member.getGrade() == Grade.VIP) {
			return discountFixAmount;
		} else {
			return 0;
		}
	}

	
}
