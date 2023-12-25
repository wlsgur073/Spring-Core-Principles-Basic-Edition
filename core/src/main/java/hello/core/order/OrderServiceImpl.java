package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService{

    /*
    * OrderServiceImpl 은 인터페이스 인 DiscountPolicy 에도, 구현체인 FixDiscountPolicy, RateDiscountPolicy 에도
    * 의존하고 있다. 그래서 아래의 코드는 인터페이스에만 의존해야 되는 DIP 를 위반하고 있다.
    * 또한, 코드를 변경하지 않고 확장해야 하는데, 구현체가 변경될 때마다 코드가 변경되므로 OCP도 위반하고 있다.
    * */
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    // final 에는 값이 할당 되어야만 사용 가능하다.
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
