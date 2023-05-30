package core.basic.discount;

import core.basic.member.Member;

public interface DiscountPolicy {

	int discount(Member member, int price);
}
