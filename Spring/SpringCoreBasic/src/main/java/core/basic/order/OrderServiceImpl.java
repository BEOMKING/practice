package core.basic.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import core.basic.discount.DiscountPolicy;
import core.basic.member.Member;
import core.basic.member.MemberRepository;

@Component
public class OrderServiceImpl implements OrderService {

//	@Autowired private MemberRepository memberRepository;
//	@Autowired private DiscountPolicy discountPolicy;
	
	private final MemberRepository memberRepository;
	private final DiscountPolicy discountPolicy;

//	@Autowired
//	public void setMemberRepository(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}
//
//	@Autowired
//	public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//		this.discountPolicy = discountPolicy;
//	}

	@Autowired
	public OrderServiceImpl(MemberRepository memberRepository,
		DiscountPolicy discountPolicy) {
		this.memberRepository = memberRepository;
		this.discountPolicy = discountPolicy;
	}

	@Override
	public Order createOrder(Long memberId, String itemName, int itemPrice) {
		Member member = memberRepository.findById(memberId);
		int discountPrice = discountPolicy.discount(member, itemPrice);
		
		return new Order(memberId, itemName, itemPrice, discountPrice);
	}

	public MemberRepository getMemberRepository() {
		return memberRepository;
	}
	
}
